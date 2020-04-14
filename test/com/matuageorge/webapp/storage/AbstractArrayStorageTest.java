package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;
import org.junit.*;

import java.util.Random;
import java.util.UUID;

import static com.matuageorge.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private Storage storage;
    public static final String UUID_1 = "uuid1";
    public static final Resume RESUME1 = new Resume(UUID_1);
    public static final String UUID_2 = "uuid2";
    public static final Resume RESUME2 = new Resume(UUID_2);
    public static final String UUID_3 = "uuid3";
    public static final Resume RESUME3 = new Resume(UUID_3);

    protected AbstractArrayStorageTest(Storage storage) {
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

    @Test
    public void save() {
        Resume resume = new Resume("new uuid");
        storage.save(new Resume("new uuid"));
        assertEquals(storage.get("new uuid"), resume);
    }

    @Test
    public void get() {
        Resume resume = new Resume(UUID_1);
        assertEquals(storage.get(UUID_1), resume);
        assertNotEquals(storage.get(UUID_2), resume);
        assertNotEquals(storage.get(UUID_3), resume);

    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        Random random = new Random();
        int randomSwitch = random.nextInt(3);

        String uuid;

        switch (randomSwitch) {
            case 0:
                uuid = UUID_1;
                break;
            case 1:
                uuid = UUID_2;
                break;
            case 2:
                uuid = UUID_3;
                break;
            default:
                uuid = null;
        }
        storage.delete(uuid);
        storage.get(uuid);
    }

    @Test
    public void getAll() {
        Resume resume1 = new Resume(UUID_1);
        Resume resume2 = new Resume(UUID_2);
        Resume resume3 = new Resume(UUID_3);

        assertEquals(3, storage.getAll().length);
        assertEquals(storage.get(UUID_1), resume1);
        assertEquals(storage.get(UUID_2), resume2);
        assertEquals(storage.get(UUID_3), resume3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    //https://www.javaguides.net/2018/08/junit-assertfail-method-example.html
    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            for (int i = 3; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume(UUID.randomUUID().toString()));
            }
        } catch (StorageException e) {
            fail("Storage overflow test failed");
        }
        storage.save(new Resume());
    }
}