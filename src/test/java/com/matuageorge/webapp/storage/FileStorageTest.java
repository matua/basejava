package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.service.ObjectStreamSerialization;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerialization()));
    }
}
