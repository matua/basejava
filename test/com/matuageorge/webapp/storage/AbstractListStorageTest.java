package com.matuageorge.webapp.storage;

public class AbstractListStorageTest extends AbstractStorageTest {
    public AbstractListStorageTest() {
        super(new SortedArraysStorage());
    }
}