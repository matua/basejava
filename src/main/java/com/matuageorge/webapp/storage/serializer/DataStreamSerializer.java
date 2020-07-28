package com.matuageorge.webapp.storage.serializer;

import com.matuageorge.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

interface ElementReader<E> {
    E read() throws IOException;
}

interface EnumReader {
    void get() throws IOException;
}

public class DataStreamSerializer implements StreamSerializer {

    private static final String NULL_STRING = "null";

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
                            writeNullSafely(dos, organization.getHomePage().getUrl());
                            writeCollection(organization.getPositions(), dos, position -> {
                                dos.writeUTF(String.valueOf(position.getStartDate()));
                                dos.writeUTF(String.valueOf(position.getEndDate()));
                                dos.writeUTF(String.valueOf(position.getTitle()));
                                writeNullSafely(dos, position.getDescription());
                            });
                        });
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType);
                }
            });
        }
    }

    private void writeNullSafely(DataOutputStream dos, String source) throws IOException {
        dos.writeUTF(source == null ? NULL_STRING : source);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            getEnums(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            getEnums(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readOrganizationSection(dis, sectionType));
            });
            return resume;
        }
    }

    private AbstractSection readOrganizationSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readCollection(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(
                        readCollection(dis, () -> {
                            String url;
                            return new Organization(
                                    new Link(dis.readUTF(), (url = dis.readUTF()).equals("null") ? null : url),
                                    readCollection(dis, () -> new Organization.Position(
                                            LocalDate.parse(dis.readUTF()),
                                            LocalDate.parse(dis.readUTF()),
                                            dis.readUTF(),
                                            dis.readUTF()
                                    ))
                            );
                        }));
            default:
                throw new IllegalStateException("Unexpected value: " + sectionType);
        }
    }

    private <E> void writeCollection(Collection<E> collection, DataOutputStream dos, ElementWriter<E> elementWriter) throws IOException {
        dos.writeInt(collection.size());
        for (E e : collection) {
            elementWriter.write(e);
        }
    }

    private <E> List<E> readCollection(DataInputStream dis, ElementReader<E> elementReader) throws IOException {
        int size = dis.readInt();
        List<E> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            collection.add(elementReader.read());
        }
        return collection;
    }

    private void getEnums(DataInputStream dis, EnumReader enumReader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            enumReader.get();
        }
    }

    private interface ElementWriter<E> {
        void write(E e) throws IOException;
    }
}