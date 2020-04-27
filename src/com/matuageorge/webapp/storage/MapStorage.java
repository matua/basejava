package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void innerUpdate(Resume resume, Object key) {
        innerSave(resume, key);
    }

    @Override
    protected void innerSave(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected void innerDelete(Object key) {
        storage.remove(key);
    }

    @Override
    protected Resume getResume(Object key) {
        return storage.get(key);
    }

    @Override
    protected boolean doesExist(Object key) {
        return storage.containsKey(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
