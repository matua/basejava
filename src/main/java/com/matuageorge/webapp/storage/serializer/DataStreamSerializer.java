package com.matuageorge.webapp.storage.serializer;

import com.matuageorge.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            contacts.forEach((contactType, description) -> {
                try {
                    dos.writeUTF(contactType.name());
                    dos.writeUTF(description);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            sections.forEach((sectionType, section) -> {
                try {
                    dos.writeUTF(sectionType.getTitle());
                    sectionWriter(section, dos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String sectionTypeString = dis.readUTF();
                SectionType sectionType = null;

                switch (sectionTypeString) {
                    case "Личные качества":
                        sectionType = SectionType.PERSONAL;
                        break;
                    case "Позиция":
                        sectionType = SectionType.OBJECTIVE;
                        break;
                    case "Достижения":
                        sectionType = SectionType.ACHIEVEMENT;
                        break;
                    case "Квалификация":
                        sectionType = SectionType.QUALIFICATIONS;
                        break;
                    case "Опыт работы":
                        sectionType = SectionType.EXPERIENCE;
                        break;
                    case "Образование":
                        sectionType = SectionType.EDUCATION;
                }
                resume.addSection(sectionType, sectionReader(dis.readUTF(), dis));
            }
            return resume;
        }
    }

    private AbstractSection sectionReader(String readUTF, DataInputStream dis) throws IOException {
        String sectionType = dis.readUTF();
        if (sectionType.equals("TextSection")) {
            TextSection section = new TextSection();
            section.setContent(dis.readUTF());
            return section;
        } else if (sectionType.equals("ListSection")) {
            ListSection section = new ListSection();
            int size = dis.readInt();
            List<String> items = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                items.add(dis.readUTF());
            }
            section.setItems(items);
            return section;
        } else {
            OrganizationSection section = new OrganizationSection();
            List<Organization> organizations = new ArrayList<>();
            section.setOrganizations(organizations);
            int sizeOfOrganizations = dis.readInt();
            for (int i = 0; i < sizeOfOrganizations; i++) {
                String name = dis.readUTF();
                String url = dis.readUTF();
                int sizeOfPositions = dis.readInt();
                List<Organization.Position> positions = new ArrayList<>();
                for (int j = 0; j < sizeOfPositions; j++) {
                    LocalDate startDate = LocalDate.parse(dis.readUTF());
                    LocalDate endDate = LocalDate.parse(dis.readUTF());
                    String title = dis.readUTF();
                    String description = dis.readUTF();
                    positions.add(new Organization.Position(startDate, endDate, title, description));
                }
                organizations.add(new Organization(new Link(name, url), positions));
            }
            return section;
        }
    }

    private void sectionWriter(AbstractSection abstractSection, DataOutputStream ops) throws IOException {
        if (abstractSection instanceof TextSection) {
            ops.writeUTF("TextSection");
            ops.writeUTF(((TextSection) abstractSection).getContent());
        } else if (abstractSection instanceof ListSection) {
            ops.writeUTF("ListSection");
            ops.writeInt(((ListSection) abstractSection).getItems().size());
            (((ListSection) abstractSection).getItems()).forEach((description -> {
                try {
                    ops.writeUTF(description);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        } else {
            ops.writeUTF("OrganizationSection");
            //writing organization
            int sizeOfOrganizations = ((OrganizationSection) abstractSection).getOrganizations().size();
            ops.writeInt(sizeOfOrganizations);
            ((OrganizationSection) abstractSection).getOrganizations().forEach(organization -> {
                try {
                    ops.writeUTF(organization.getHomePage().getName());
                    ops.writeUTF(organization.getHomePage().getUrl());
                    //writing positions
                    int sizeOfPositions = organization.getPositions().size();
                    ops.writeInt(sizeOfPositions);
                    organization.getPositions().forEach(position -> {
                        try {
                            ops.writeUTF(String.valueOf(position.getStartDate()));
                            ops.writeUTF(String.valueOf(position.getEndDate()));
                            ops.writeUTF(String.valueOf(position.getTitle()));
                            ops.writeUTF(String.valueOf(position.getDescription()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}