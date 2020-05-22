package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.ResumeTestData;
import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.Resume;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("/Users/matua/GoogleDrive/Private/Обучение/Programming/Java/JavaBase/basejava/src/main/java/com/matuageorge/Resumes");
    public static final Resume FILE_RESUME = ResumeTestData.returnTestResume();
    public static final String UUID_2 = "uuid_2";
    public static final Resume RESUME2 = new Resume(UUID_2, "Matua");
    public static final String UUID_3 = "uuid_3";
    public static final Resume RESUME3 = new Resume(UUID_3, "Petrov");
    public static final Resume RESUME_NEW_UUID = new Resume(
            "new_uuid", "Smith");
    protected final Storage storage;
    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(FILE_RESUME);
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
        Resume resume = new Resume(FILE_RESUME.getUuid(), "Григорий Кислин");
        storage.update(resume);
        assertSame(resume, storage.get(FILE_RESUME.getUuid()));
        assertSame(resume, storage.get(FILE_RESUME.getUuid()));
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
        storage.save(FILE_RESUME);
    }


    @Test
    public void get() {
        Resume resume = ResumeTestData.returnTestResume();
        assertEquals(storage.get(FILE_RESUME.getUuid()), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(FILE_RESUME.getUuid());
        assertEquals(2, storage.size());
        storage.get(FILE_RESUME.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> storageToCompare = storage.getAllSorted();
        assertEquals(3, storageToCompare.size());
        assertEquals(Arrays.asList(FILE_RESUME, RESUME2, RESUME3), storageToCompare);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}
