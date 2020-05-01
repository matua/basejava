package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void innerUpdate(Resume resume, Object key) {
        storage[(int) key] = resume;
    }

    @Override
    protected void innerSave(Resume resume, Object key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage Overflow", resume.getUuid());
        } else {
            insertResume(resume, (int) key);
            size++;
        }
    }

    @Override
    protected Resume innerGet(Object key) {
        return storage[(int) key];
    }

    @Override
    protected void innerDelete(Object key) {
        replaceResume((int) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract void replaceResume(int index);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract Integer getKey(String uuid);

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected List<Resume> innerGetAllSorted() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
