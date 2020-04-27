package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void storageOverflow() {
        throw new ExistStorageException("Unlimited");
    }
}