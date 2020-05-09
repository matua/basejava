package com.matuageorge.webapp;

import com.matuageorge.webapp.exception.StorageException;

import java.io.File;
import java.util.Objects;

public class RecursiveSearch {
    public static void main(String[] args) {
        File file = new File("src/main/java/com/matuageorge/webapp");
        recursiveSearch(file);
    }

    public static void recursiveSearch(File file) {
        Objects.requireNonNull(file, "directory must not be null");
        try {
            File[] listOfFiles = file.listFiles();
        } catch (NullPointerException e) {
            throw new StorageException("Directory is empty", file.getName());
        }

        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                recursiveSearch(f);
            }
            System.out.println("\t" + f.getName());
        }
    }
}
