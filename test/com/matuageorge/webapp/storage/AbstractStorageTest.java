package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.Resume;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {

    public static final Resume RESUME_NEW_UUID = new Resume(
            "new_uuid", "Smith");
    public static final String UUID_1 = "uuid_1";
    public static final Resume RESUME1 = new Resume(UUID_1, "Ivanov");
    public static final String UUID_2 = "uuid_2";
    public static final Resume RESUME2 = new Resume(UUID_2, "Matua");
    public static final String UUID_3 = "uuid_3";
    public static final Resume RESUME3 = new Resume(UUID_3, "Petrov");
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("All tests passed!");
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "Ivanov");
        storage.update(resume);
        assertSame(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_NEW_UUID);
    }

    @Test
    public void save() {
        storage.save(RESUME_NEW_UUID);
        assertEquals(storage.get("new_uuid"), RESUME_NEW_UUID);
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME1);
    }


    @Test
    public void get() {
        Resume resume = new Resume(UUID_1, "Ivanov");
        assertEquals(storage.get(UUID_1), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> storageToCompare = storage.getAllSorted();
        assertEquals(3, storageToCompare.size());
        assertEquals(Arrays.asList(RESUME1, RESUME2, RESUME3), storageToCompare);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}