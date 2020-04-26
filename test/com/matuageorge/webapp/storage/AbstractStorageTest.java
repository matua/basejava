package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.matuageorge.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    public static final Resume RESUME_NEW_UUID = new Resume(
            "new uuid");
    private final Storage storage;
    public static final String UUID_1 = "uuid1";
    public static final Resume RESUME1 = new Resume(UUID_1);
    public static final String UUID_2 = "uuid2";
    public static final Resume RESUME2 = new Resume(UUID_2);
    public static final String UUID_3 = "uuid3";
    public static final Resume RESUME3 = new Resume(UUID_3);

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
        Resume resume = new Resume(UUID_1);
        storage.update(resume);
        assertEquals(RESUME1, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_NEW_UUID);
    }

    @Test
    public void save() {
        storage.save(RESUME_NEW_UUID);
        assertEquals(storage.get("new uuid"), RESUME_NEW_UUID);
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME1);
    }


    @Test
    public void get() {
        Resume resume = new Resume(UUID_1);
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
    public void getAll() {
        Resume[] expectedResumes = {RESUME1, RESUME2, RESUME3};

        assertEquals(3, storage.getAll().length);
        assertArrayEquals(expectedResumes, storage.getAll());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    //https://www.javaguides.net/2018/08/junit-assertfail-method-example.html
    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume(UUID.randomUUID().toString()));
            }
        } catch (StorageException e) {
            fail("Storage overflow test failed");
        }
        storage.save(new Resume());
    }
}