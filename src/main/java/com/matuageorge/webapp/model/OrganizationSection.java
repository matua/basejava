package com.matuageorge.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Organization> organizations;

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public OrganizationSection() {
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public OrganizationSection setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations != null ? organizations.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Organization org : organizations) {
            result.append(org);
        }
        return result.toString();
    }
}