package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageSearchByResume extends AbstractStorage {
    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void innerUpdate(Resume resume, Object r) {
        innerSave(resume, r);
    }

    @Override
    protected void innerSave(Resume resume, Object r) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void innerDelete(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected Resume getResume(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected boolean doesExist(Object resume) {
        return storage.containsKey(resume);
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
