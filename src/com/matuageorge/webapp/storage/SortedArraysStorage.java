package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.Arrays;

public class SortedArraysStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume toSearch = new Resume();
        toSearch.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, toSearch);
    }
}
