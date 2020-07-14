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
                    dos.writeUTF(sectionType.name());
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            dos.writeUTF(((TextSection) section).getContent());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            dos.writeInt(((ListSection) section).getItems().size());
                            (((ListSection) section).getItems()).forEach((description -> {
                                try {
                                    dos.writeUTF(description);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            //writing organization
                            int sizeOfOrganizations = ((OrganizationSection) section).getOrganizations().size();
                            dos.writeInt(sizeOfOrganizations);
                            ((OrganizationSection) section).getOrganizations().forEach(organization -> {
                                try {
                                    dos.writeUTF(organization.getHomePage().getName());
                                    dos.writeUTF(organization.getHomePage().getUrl());
                                    //writing positions
                                    int sizeOfPositions = organization.getPositions().size();
                                    dos.writeInt(sizeOfPositions);
                                    organization.getPositions().forEach(position -> {
                                        try {
                                            dos.writeUTF(String.valueOf(position.getStartDate()));
                                            dos.writeUTF(String.valueOf(position.getEndDate()));
                                            dos.writeUTF(String.valueOf(position.getTitle()));
                                            dos.writeUTF(String.valueOf(position.getDescription()));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            break;
                    }
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
                SectionType sectionType;
                AbstractSection section;

                switch (sectionTypeString) {
                    case "PERSONAL":
                        sectionType = SectionType.PERSONAL;
                        section = new TextSection();
                        ((TextSection) section).setContent(dis.readUTF());
                        resume.addSection(sectionType, section);
                        break;
                    case "OBJECTIVE":
                        sectionType = SectionType.OBJECTIVE;
                        section = new TextSection();
                        ((TextSection) section).setContent(dis.readUTF());
                        resume.addSection(sectionType, section);
                        break;
                    case "ACHIEVEMENT":
                        sectionType = SectionType.ACHIEVEMENT;
                        section = new ListSection();
                        int sizeOfSection = dis.readInt();
                        List<String> items = new ArrayList<>();
                        for (int x = 0; x < sizeOfSection; x++) {
                            items.add(dis.readUTF());
                        }
                        ((ListSection) section).setItems(items);
                        resume.addSection(sectionType, section);
                        break;
                    case "QUALIFICATIONS":
                        sectionType = SectionType.QUALIFICATIONS;
                        section = new ListSection();
                        sizeOfSection = dis.readInt();
                        items = new ArrayList<>();
                        for (int x = 0; x < sizeOfSection; x++) {
                            items.add(dis.readUTF());
                        }
                        ((ListSection) section).setItems(items);
                        resume.addSection(sectionType, section);
                        break;
                    case "EXPERIENCE":
                        sectionType = SectionType.EXPERIENCE;
                        section = new OrganizationSection();
                        List<Organization> organizations = new ArrayList<>();
                        ((OrganizationSection) section).setOrganizations(organizations);
                        int sizeOfOrganizations = dis.readInt();
                        readOrganizationSection(dis, organizations, sizeOfOrganizations);
                        resume.addSection(sectionType, section);
                        break;
                    case "EDUCATION":
                        sectionType = SectionType.EDUCATION;
                        section = new OrganizationSection();
                        organizations = new ArrayList<>();
                        ((OrganizationSection) section).setOrganizations(organizations);
                        sizeOfOrganizations = dis.readInt();
                        readOrganizationSection(dis, organizations, sizeOfOrganizations);
                        resume.addSection(sectionType, section);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionTypeString);
                }
            }
            return resume;
        }
    }

    private void readOrganizationSection(DataInputStream dis, List<Organization> organizations, int sizeOfOrganizations) throws IOException {
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
    }
}