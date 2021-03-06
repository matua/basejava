package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected Integer getKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void replaceResume(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void insertResume(Resume resume, int index) {
            storage[size] = resume;
    }
}