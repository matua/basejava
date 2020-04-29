package com.matuageorge.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ArrayStorageTest.class, SortedArrayStorageTest.class, ListStorageTest.class, MapStorageTest.class, MapStorageByResumeTest.class})
public class BaseStorageTest {
}