package com.matuageorge.webapp.model;

import java.util.List;
import java.util.Objects;

public class BulletsSection extends Section {
    private final List<String> bullets;

    public BulletsSection(String description, List<String> bullets) {
        super(description);
        this.bullets = bullets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletsSection that = (BulletsSection) o;

        return Objects.equals(bullets, that.bullets);
    }

    @Override
    public int hashCode() {
        return bullets != null ? bullets.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (String bullet : bullets) {
            result.append("\t• ").append(bullet).append("\n");
        }

        return result.toString();
    }
}
