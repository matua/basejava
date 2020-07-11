package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void innerUpdate(Resume resume, String key) {
        innerSave(resume, key);
    }

    @Override
    protected void innerSave(Resume resume, String key) {
        storage.put(key, resume);
    }

    @Override
    protected void innerDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected Resume innerGet(String key) {
        return storage.get(key);
    }

    @Override
    protected boolean isExist(String key) {
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