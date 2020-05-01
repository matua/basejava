package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    protected Resume innerGet(Object key) {
        return storage.get(key);
    }

    @Override
    protected boolean isExist(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected List<Resume> innerGetAllSorted() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
