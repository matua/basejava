package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;
import org.junit.Test;

import java.util.UUID;

import static com.matuageorge.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.fail;

public abstract class AbstractArraysStorageTest extends AbstractStorageTest {
    protected AbstractArraysStorageTest(Storage storage) {
        super(storage);
    }

    //https://www.javaguides.net/2018/08/junit-assertfail-method-example.html
    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume(UUID.randomUUID().toString(), "name_" + i));
            }
        } catch (StorageException e) {
            fail("Storage overflow test failed");
        }
        storage.save(new Resume("Some Name"));
    }
}
