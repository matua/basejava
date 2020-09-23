package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.Config;
import com.matuageorge.webapp.ResumeTestData;
import com.matuageorge.webapp.ResumeTestData2;
import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    public static final Resume RESUME_1 = ResumeTestData.returnTestResume();
    public static final String UUID_1 = RESUME_1.getUuid();
    public static final Resume RESUME_2 = ResumeTestData2.returnTestResume();
    public static final String UUID_2 = RESUME_2.getUuid();

    public static final String UUID_3 = String.valueOf(UUID.randomUUID());
    public static final Resume RESUME_3 = new Resume(UUID_3, "Petrov");
    public static final String NEW_UUID = String.valueOf(UUID.randomUUID());
    public static final Resume RESUME_NEW_UUID = new Resume(NEW_UUID, "Smith");
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        RESUME_2.addContact(ContactType.PHONE, "0797653728");
        storage.save(RESUME_2);
        RESUME_3.addContact(ContactType.SKYPE, "sdfgh");
        RESUME_3.addContact(ContactType.GITHUB, "sdfghj");
        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("asd"));
        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("aswerwrd"));
        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<>(Arrays.asList("asd", "Sdf", "sdf"))));
        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList("asd", "Sdf", "sdf"))));
        storage.save(RESUME_3);
        RESUME_NEW_UUID.addContact(ContactType.GITHUB, "sdfghj");
        RESUME_NEW_UUID.addSection(SectionType.PERSONAL, new TextSection("asd"));
        RESUME_NEW_UUID.addSection(SectionType.OBJECTIVE, new TextSection("aswerwrd"));
        RESUME_NEW_UUID.addSection(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<>(Arrays.asList("asd", "Sdf", "sdf"))));
        RESUME_NEW_UUID.addSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList("asd", "Sdf", "sdf"))));

    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = RESUME_1;
        newResume.addContact(ContactType.PHONE, "1111111111");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_NEW_UUID);
    }

    @Test
    public void save() {
        storage.save(RESUME_NEW_UUID);
        assertEquals(RESUME_NEW_UUID, storage.get(NEW_UUID));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        assertEquals(RESUME_2, storage.get(RESUME_2.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertEquals(2, storage.size());
        storage.get(RESUME_1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), list);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}