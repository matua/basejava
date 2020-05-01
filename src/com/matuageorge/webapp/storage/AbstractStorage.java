package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object key = doesNotExistCheck(resume.getUuid());
        innerUpdate(resume, key);
    }

    @Override
    public void save(Resume resume) {
        Object key = doesExistCheck(resume.getUuid());
        innerSave(resume, key);
    }

    public Resume get(String uuid) {
        Object key = doesNotExistCheck(uuid);
        return innerGet(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = doesNotExistCheck(uuid);
        innerDelete(key);
    }

    private Object doesNotExistCheck(String uuid) {
        Object key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object doesExistCheck(String uuid) {
        Object key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = innerGetAllSorted();
        Collections.sort(result);
        return result;
    }

    protected abstract Object getKey(String uuid);

    protected abstract void innerUpdate(Resume resume, Object key);

    protected abstract void innerSave(Resume resume, Object key);

    protected abstract void innerDelete(Object key);

    protected abstract Resume innerGet(Object key);

    protected abstract boolean isExist(Object key);

    protected abstract List<Resume> innerGetAllSorted();
}
