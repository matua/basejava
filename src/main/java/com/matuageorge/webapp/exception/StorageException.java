package com.matuageorge.webapp.exception;

import java.io.IOException;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }


    public StorageException(String message, IOException e) {
        this(message, null, e);
    }

    public StorageException(String message) {
        this(message, null, null);
    }
}
