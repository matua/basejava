package com.matuageorge.webapp.storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(propertiesGetter().getProperty("db.url"), propertiesGetter().getProperty("db.user"), propertiesGetter().getProperty("db.password")));
    }

    private static Properties propertiesGetter() {
        FileReader reader = null;
        try {
            reader = new FileReader("/Users/matua/IdeaProjects/basejava/config/resumes.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}