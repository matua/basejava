package com.matuageorge.webapp;

import java.io.File;
import java.util.Objects;

public class RecursiveSearch {
    public static void main(String[] args) {
        File file = new File("src/main/java/com/matuageorge/webapp");
        recursiveSearch(file);
    }

    public static void recursiveSearch(File file) {
        Objects.requireNonNull(file, "directory must not be null");
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                recursiveSearch(f);
            }
            System.out.println("\t" + f.getName());
        }
    }
}
