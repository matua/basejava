package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Resume innerGet(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (innerGet(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void innerUpdate(Resume resume, Object key) {
        storage.set((Integer) key, resume);
    }

    @Override
    protected void innerSave(Resume resume, Object key) {
        storage.add(resume);
    }

    @Override
    protected void innerDelete(Object key) {
        storage.remove(((Integer) key).intValue());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected List<Resume> innerGetAllSorted() {
        return storage;
    }
}
