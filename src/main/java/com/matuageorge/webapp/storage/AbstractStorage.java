package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.ExistStorageException;
import com.matuageorge.webapp.exception.NotExistStorageException;
import com.matuageorge.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void update(Resume resume) {
        SK key = doesNotExistCheck(resume.getUuid());
        innerUpdate(resume, key);
    }

    @Override
    public void save(Resume resume) {
        SK key = doesExistCheck(resume.getUuid());
        innerSave(resume, key);
    }

    public Resume get(String uuid) {
        SK key = doesNotExistCheck(uuid);
        return innerGet(key);
    }

    @Override
    public void delete(String uuid) {
        SK key = doesNotExistCheck(uuid);
        innerDelete(key);
    }

    private SK doesNotExistCheck(String uuid) {
        SK key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK doesExistCheck(String uuid) {
        SK key = getKey(uuid);
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

    protected abstract SK getKey(String uuid);

    protected abstract void innerUpdate(Resume resume, SK key);

    protected abstract void innerSave(Resume resume, SK key);

    protected abstract void innerDelete(SK key);

    protected abstract Resume innerGet(SK key);

    protected abstract boolean isExist(SK key);

    protected abstract List<Resume> innerGetAllSorted();

}
