package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageSearchByResume extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void innerUpdate(Resume resume, Resume r) {
        innerSave(resume, r);
    }

    @Override
    protected void innerSave(Resume resume, Resume r) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void innerDelete(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected Resume innerGet(Resume resume) {
        return resume;
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
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
