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
        storage.put((String) key, resume);
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
    protected boolean found(Object key) {
        return storage.containsKey(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = new Resume[storage.size()];
        int count = 0;
        for (Map.Entry<String, Resume> resumes : storage.entrySet()) {
            result[count] = resumes.getValue();
            count++;
        }
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
