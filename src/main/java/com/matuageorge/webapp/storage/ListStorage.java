package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Resume innerGet(Integer key) {
        return storage.get(key);
    }

    @Override
    protected boolean isExist(Integer key) {
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
    protected void innerUpdate(Resume resume, Integer key) {
        storage.set(key, resume);
    }

    @Override
    protected void innerSave(Resume resume, Integer key) {
        storage.add(resume);
    }

    @Override
    protected void innerDelete(Integer key) {
        storage.remove(key.intValue());
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
