package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

public class MapStorage extends AbstractStorage {
    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    protected void replaceResume(int index) {

    }

    @Override
    protected void insertResume(Resume resume, int index) {

    }

    @Override
    protected void innerUpdate(Resume resume, int index) {

    }

    @Override
    protected void innerSave(Resume resume, int index) {

    }

    @Override
    protected void innerDelete(int index) {

    }

    @Override
    protected Resume getResume(int index) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
