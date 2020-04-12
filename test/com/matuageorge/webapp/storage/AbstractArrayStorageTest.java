package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;
import org.junit.*;

import java.util.Random;
import java.util.UUID;

public abstract class AbstractArrayStorageTest {

    private Storage storage;
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("All tests passed!");
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid1");
        storage.update(resume);
        Assert.assertEquals(storage.get("uuid1"), resume);
        Assert.assertNotEquals(storage.get("uuid2"), resume);
        Assert.assertNotEquals(storage.get("uuid3"), resume);
    }

    @Test
    public void save() {
        Resume resume = new Resume("new uuid");
        storage.save(new Resume("new uuid"));
        Assert.assertEquals(storage.get("new uuid"), resume);
        Assert.assertNotEquals(storage.get("uuid1"), resume);
        Assert.assertNotEquals(storage.get("uuid2"), resume);
        Assert.assertNotEquals(storage.get("uuid3"), resume);
    }

    @Test
    public void get() {
        Resume resume = new Resume("uuid1");
        Assert.assertEquals(storage.get("uuid1"), resume);
        Assert.assertNotEquals(storage.get("uuid2"), resume);
        Assert.assertNotEquals(storage.get("uuid3"), resume);

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
                uuid = "uuid1";
                break;
            case 1:
                uuid = "uuid2";
                break;
            case 2:
                uuid = "uuid3";
                break;
            default:
                uuid = null;
        }
        storage.delete(uuid);
        storage.get(uuid);
    }

    @Test
    public void getAll() {
        Resume resume1 = new Resume("uuid1");
        Resume resume2 = new Resume("uuid2");
        Resume resume3 = new Resume("uuid3");

        Assert.assertEquals(3, storage.getAll().length);
        Assert.assertEquals(storage.get("uuid1"), resume1);
        Assert.assertEquals(storage.get("uuid2"), resume2);
        Assert.assertEquals(storage.get("uuid3"), resume3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    //https://www.javaguides.net/2018/08/junit-assertfail-method-example.html
    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            for (int i = 3; i <= 9_999; i++) {
                storage.save(new Resume(UUID.randomUUID().toString()));
            }
        } catch (StorageException e) {
            Assert.fail("Storage overflow test failed");
        }
        storage.save(new Resume());
    }
}