package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    /*index of the search key, if it is contained in the array within the specified range; otherwise,
    (-(insertion point) - 1). The insertion point is defined as the point at which the key would be
    inserted into the array: the index of the first element in the range greater than the key,
     or toIndex if all elements in the range are less than the specified key. Note that this guarantees
      that the return value will be >= 0 if and only if the key is found.*/
    @Override
    protected Integer getKey(String uuid) {
        Resume toSearch = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, toSearch);
    }

    @Override
    protected void replaceResume(int index) {
        int replacedResume = size - index - 1;
        if (replacedResume > 0) {
            System.arraycopy(storage, index + 1, storage, index, replacedResume);
        }
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        int insertionPoint = -index - 1;
        System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        storage[insertionPoint] = resume;
    }
}
