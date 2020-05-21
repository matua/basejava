package com.matuageorge.webapp;

import java.io.File;

public class RecursiveSearch {

    private static int step;
    private static StringBuilder tab;

    public static void main(String[] args) {
        File file = new File("/Users/matua/GoogleDrive/Private/Обучение/Programming/Java/JavaBase/basejava/src/main/java/com/matuageorge");
        recursivePrint(file, "");
    }

    public static void recursivePrint(File dir, String tab) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(tab + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(tab + file.getName());
                    recursivePrint(file, tab + "\t");
                }
            }
        }
    }
}
