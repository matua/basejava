package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void innerUpdate(Resume resume, Integer key) {
        storage[key] = resume;
    }

    @Override
    protected void innerSave(Resume resume, Integer key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage Overflow", resume.getUuid());
        } else {
            insertResume(resume, key);
            size++;
        }
    }

    @Override
    protected Resume innerGet(Integer key) {
        return storage[key];
    }

    @Override
    protected void innerDelete(Integer key) {
        replaceResume(key);
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
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected List<Resume> innerGetAllSorted() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
