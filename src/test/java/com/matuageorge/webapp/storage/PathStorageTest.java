package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.service.ObjectStreamSerialization;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerialization()));
    }
}
