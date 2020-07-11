package com.matuageorge.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private List<String> items;

    public ListSection(List<String> items) {
        this.items = items;
    }

    public ListSection() {
    }

    public List<String> getItems() {
        return items;
    }

    public ListSection setItems(List<String> items) {
        this.items = items;
        return this;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String bullet : items) {
            result.append("\t• ").append(bullet).append("\n");
        }
        return result.toString();
    }
}