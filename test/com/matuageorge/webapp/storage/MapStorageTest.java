package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void storageOverflow() {
        throw new ExistStorageException("Unlimited");
    }
}