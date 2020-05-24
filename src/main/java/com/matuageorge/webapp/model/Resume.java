package com.matuageorge.webapp.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;
    private final String uuid;
    private final String fullName;
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setSections(Map<SectionType, AbstractSection> sections) {
        this.sections = sections;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
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
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
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
        for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
            sectionBuilder
                    .append(section.getKey().getTitle())
                    .append(":\n")
                    .append(section.getValue())
                    .append("\n");
        }
        return result.append(uuid).append("\n").append(fullName).append("\n\n")
                .append(contactsBuilder)
                .append("\n")
                .append(sectionBuilder).toString();
    }
}
