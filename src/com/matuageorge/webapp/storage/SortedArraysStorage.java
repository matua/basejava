package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

public class SortedArraysStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }
}
