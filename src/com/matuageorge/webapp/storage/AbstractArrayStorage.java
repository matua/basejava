package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = search(uuid);

        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    protected abstract int search(String uuid);
}
