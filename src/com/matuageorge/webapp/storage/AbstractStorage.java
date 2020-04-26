package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume resume) {
        Object key = getKey(resume.getUuid());

        if (found(key)) {
            innerUpdate(resume, key);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        Object key = getKey(resume.getUuid());

        if (found(key)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            innerSave(resume, key);
        }
    }

    public Resume get(String uuid) {
        Object key = getKey(uuid);
        if (found(key)) {
            return getResume(key);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        Object key = getKey(uuid);

        if (found(key)) {
            innerDelete(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Object getKey(String uuid);

    protected abstract void innerUpdate(Resume resume, Object key);

    protected abstract void innerSave(Resume resume, Object key);

    protected abstract void innerDelete(Object key);

    protected abstract Resume getResume(Object key);

    protected abstract boolean found(Object key);
}
