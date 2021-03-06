package com.matuageorge.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    public TextSection() {
    }

    public String getContent() {
        return content;
    }

    public TextSection setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }
}