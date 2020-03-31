package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int found = searchByObject(r);

        if (found == -1) {
            if (size == storage.length) {
                System.out.println("The storage is full. Cannot complete the operation.");
            } else {
                storage[size] = r;
                size++;
            }
        }
    }

    public void update(Resume r, Resume z) {
        int foundOld = searchByObject(r);
        int foundNew = searchByObject(z);

        if (foundOld != -1) {
            if (foundNew == -1) {
                storage[foundOld] = z;
            }
        }
    }

    public Resume get(String uuid) {
        int found = searchByUUID(uuid);

        if (found != -1) {
            return storage[found];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        int found = searchByUUID(uuid);
        ;

        if (found != -1) {
            storage[found] = storage[size - 1];
            storage[size - 1] = null;
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes;

        resumes = Arrays.copyOfRange(storage, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }

    public int searchByObject(Resume r) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(r.toString())) {
                System.out.println("Search done. uuid exists");
                return i;
            }
        }
        System.out.println("Search done. No such uuid");
        return -1;
    }

    public int searchByUUID(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                System.out.println("Search done. uuid exists");
                return i;
            }
        }
        System.out.println("Search done. No such uuid");
        return -1;
    }
}
