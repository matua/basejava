package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        boolean notFound = true;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(r.toString())) {
                System.out.println("Such uuid already exists");
                notFound = false;
            }
        }
        if (notFound) {
            if (size == storage.length) {
                System.out.println("The storage is full. Cannot complete the operation.");
            } else {
                storage[size] = r;
                size++;
            }
        }
    }

    public void update(Resume r, Resume z) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(r.toString())) {
                storage[i] = z;
                found = true;
                break;
            }
        }
        if (!found)
            System.out.println("No such uuid");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("No such uuid");
        return null;
    }

    public void delete(String uuid) {
        boolean found = true;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                for (int j = i; j < size - 1; j++) {
                    storage[j] = storage[j + 1];
                }
                found = false;
                size--;
                break;
            }
        }
        if (!found)
            System.out.println("No such uuid");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];

        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}
