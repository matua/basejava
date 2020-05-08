package com.matuageorge.webapp.model;

import java.time.YearMonth;
import java.util.Map;
import java.util.Objects;

public class Organization {
    private WebLink webLink;
    private Map<YearMonth, YearMonth> period;
    private Section description;

    public Organization(WebLink webLink, Map<YearMonth, YearMonth> period, Section description) {
        this.webLink = webLink;
        this.period = period;
        this.description = description;
    }

    public WebLink getWebLink() {
        return webLink;
    }

    public void setWebLink(WebLink webLink) {
        this.webLink = webLink;
    }

    public Map<YearMonth, YearMonth> getPeriod() {
        return period;
    }

    public void setPeriod(Map<YearMonth, YearMonth> period) {
        this.period = period;
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
        if (!Objects.equals(period, that.period)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = webLink != null ? webLink.hashCode() : 0;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        StringBuilder periods = new StringBuilder();

        for (Map.Entry<YearMonth, YearMonth> period : period.entrySet()) {
            periods
                    .append(period.getKey())
                    .append(" - ")
                    .append(period.getValue())
                    .append("\n");
        }
        return result.append(getWebLink())
                .append("\n")
                .append(periods)
                .append(description)
                .append("\n")
                .toString();

    }
}




