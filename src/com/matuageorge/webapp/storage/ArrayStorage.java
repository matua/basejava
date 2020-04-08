package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
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
        if (index < 0) {
            storage[size] = resume;
        } else {
            storage[index] = resume;
        }
    }
}
