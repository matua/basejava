package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10_000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = search(resume.getUuid());

        if (index == -1) {
            if (size == storage.length) {
                System.out.println("The storage is full. Cannot complete the operation.");
            } else {
                storage[size] = resume;
                size++;
            }
        }
    }

    public void update(Resume resume) {
        int index = search(resume.getUuid());

        if (index != -1) {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = search(uuid);

        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = search(uuid);

        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int search(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                System.out.format("Search done. uuid %s exists", storage[i].getUuid());
                return i;
            }
        }
        System.out.println("Search done. No such uuid");
        return -1;
    }
}
