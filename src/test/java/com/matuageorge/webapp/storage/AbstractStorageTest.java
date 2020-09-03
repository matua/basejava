package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.Config;
import com.matuageorge.webapp.ResumeTestData;
import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    public static final Resume RESUME_1 = ResumeTestData.returnTestResume();
    public static final String UUID_2 = String.valueOf(UUID.randomUUID());
    public static final Resume RESUME_2 = new Resume(UUID_2, "Matua");
    public static final String UUID_3 = String.valueOf(UUID.randomUUID());
    public static final Resume RESUME_3 = new Resume(UUID_3, "Petrov");
    public static final String NEW_UUID = String.valueOf(UUID.randomUUID());
    public static final Resume RESUME_NEW_UUID = new Resume(
            NEW_UUID, "Smith");
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected AbstractStorageTest() {
        storage = null;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_2, "Matua");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_2));
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