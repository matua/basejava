package com.matuageorge.webapp.storage;

public class AbstractListStorageTest extends AbstractArrayStorageTest {
    public AbstractListStorageTest() {
        super(new SortedArraysStorage());
    }
}