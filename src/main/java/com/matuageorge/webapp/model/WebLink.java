package com.matuageorge.webapp.model;

import java.net.URL;
import java.util.Objects;

public class WebLink {
    private final String name;
    private URL url;

    public WebLink(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public WebLink(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebLink webLink = (WebLink) o;

        if (!Objects.equals(name, webLink.name)) return false;
        return Objects.equals(url, webLink.url);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (url != null) {
            return name + " (" + url + ")";
        } else {
            return name;
        }
    }
}
