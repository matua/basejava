package com.matuageorge.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {


    private final String uuid;
    private final String fullName;
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {

        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public void setSections(Map<SectionType, Section> sections) {
        this.sections = sections;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result != 0 ? result : uuid.compareTo(o.uuid);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        StringBuilder contactsBuilder = new StringBuilder();

        for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
            contactsBuilder.append(contact.getKey())
                    .append(":")
                    .append(contact.getValue())
                    .append("\n");
        }

        StringBuilder sectionBuilder = new StringBuilder();

        for (Map.Entry<SectionType, Section> section : sections.entrySet()) {
            sectionBuilder
                    .append(section.getKey().getTitle())
                    .append(":\n")
                    .append(section.getValue())
                    .append("\n");
        }

        return result.append(fullName).append("\n\n")
                .append(contactsBuilder)
                .append("\n")
                .append(sectionBuilder).toString();
    }
}
