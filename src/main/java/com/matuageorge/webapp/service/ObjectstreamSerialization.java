package com.matuageorge.webapp.service;

import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;

import java.io.*;

public class ObjectstreamSerialization implements ObjectStream {
    @Override
    public void doWrite(Resume resume, BufferedOutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume fileToResume(BufferedInputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Read resume error", null, e);
        }
    }
}
