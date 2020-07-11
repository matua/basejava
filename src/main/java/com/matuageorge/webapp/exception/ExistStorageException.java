package com.matuageorge.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exists", uuid);
    }
}