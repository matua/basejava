package com.matuageorge.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Organization {
    private WebLink webLink;
    private YearMonth start;
    private YearMonth end;
    private Section description;

    public Organization(WebLink webLink, YearMonth start, YearMonth end, Section description) {
        this.webLink = webLink;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public WebLink getWebLink() {
        return webLink;
    }

    public void setWebLink(WebLink webLink) {
        this.webLink = webLink;
    }

    public YearMonth getStart() {
        return start;
    }

    public void setStart(YearMonth start) {
        this.start = start;
    }

    public YearMonth getEnd() {
        return end;
    }

    public void setEnd(YearMonth end) {
        this.end = end;
    }

    public Section getDescription() {
        return description;
    }

    public void setDescription(Section description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(webLink, that.webLink)) return false;
        if (!Objects.equals(start, that.start)) return false;
        if (!Objects.equals(end, that.end)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = webLink != null ? webLink.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.getWebLink() + "\n" +
                this.getStart() + " - " + this.getEnd() + "\n" +
                this.getDescription() + "\n";
    }
}




