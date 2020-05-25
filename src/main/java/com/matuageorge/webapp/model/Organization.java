package com.matuageorge.webapp.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private WebLink webLink;
    private List<Position> positions;
    private AbstractSection description;

    public Organization(WebLink webLink, List<Position> positions, AbstractSection description) {
        this.webLink = webLink;
        this.positions = positions;
        this.description = description;
    }

    public WebLink getWebLink() {
        return webLink;
    }

    public void setWebLink(WebLink webLink) {
        this.webLink = webLink;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public AbstractSection getDescription() {
        return description;
    }

    public void setDescription(AbstractSection description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Organization(" + webLink + "," + positions + "," + description + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(webLink, that.webLink) &&
                Objects.equals(positions, that.positions) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webLink, positions, description);
    }

    public static class Position implements Serializable {
        private final YearMonth startDate;
        private final YearMonth endDate;
        public Position(YearMonth startDate, YearMonth endDate) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public YearMonth getEndDate() {
            return endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (!Objects.equals(startDate, position.startDate)) return false;
            return Objects.equals(endDate, position.endDate);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return this.startDate + " - " + this.endDate + "\n";
        }
    }
}
