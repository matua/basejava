package com.matuageorge.webapp.storage.serializer;

import com.matuageorge.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(contacts.entrySet(), dos, e -> {
                dos.writeUTF(e.getKey().name());
                dos.writeUTF(e.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();
            writeCollection(sections.entrySet(), dos, e -> {
                SectionType sectionType = e.getKey();
                AbstractSection section = e.getValue();
                dos.writeUTF(sectionType.name());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(((ListSection) section).getItems(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        //writing organization
                        writeCollection(((OrganizationSection) section).getOrganizations(), dos, organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl());
                            writeCollection(organization.getPositions(), dos, position -> {
                                dos.writeUTF(String.valueOf(position.getStartDate()));
                                dos.writeUTF(String.valueOf(position.getEndDate()));
                                dos.writeUTF(String.valueOf(position.getTitle()));
                                dos.writeUTF(String.valueOf(position.getDescription()));
                            });
                        });
                        break;
                    default:
                        throw new IllegalStateException(String.format("Unexpected value: %s", sectionType));
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

    private <E> void writeCollection(Collection<E> collection, DataOutputStream ops, ElementWriter<E> elementWriter) throws IOException {
        ops.writeInt(collection.size());
        for (E e : collection) {
            elementWriter.write(e);
        }
    }

    private interface ElementWriter<E> {
        void write(E e) throws IOException;
    }
}