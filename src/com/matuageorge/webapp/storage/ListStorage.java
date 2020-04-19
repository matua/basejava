package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }


    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (getResume(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void replaceResume(int index) {
        innerUpdate(storage.get(storage.size() - 1), index);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        innerSave(resume, index);
    }

    @Override
    protected void innerUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void innerSave(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void innerDelete(int index) {
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
