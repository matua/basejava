package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;
import com.matuageorge.webapp.service.ObjectstreamSerialization;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final ObjectstreamSerialization objectstreamSerialization;

    protected FileStorage(File directory, ObjectstreamSerialization objectstreamSerialization) {
        this.objectstreamSerialization = objectstreamSerialization;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not a directory");
        }
        if (!directory.canRead() && !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void innerUpdate(Resume resume, File file) {
        try {
            objectstreamSerialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", resume.getUuid(), e);
        }
    }

    @Override
    protected void innerSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Error creating file " + file.getAbsolutePath(), file.getName(), e);
        }
        innerUpdate(resume, file);
    }

    @Override
    protected void innerDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File does not exist", file.getName());
        }
    }

    @Override
    protected Resume innerGet(File file) {
        try {
            return objectstreamSerialization.fileToResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }


    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> innerGetAllSorted() {
        File[] files = directory.listFiles();
        checkNull(files == null, "Directory read error");
        List<Resume> list = new ArrayList<>(Objects.requireNonNull(files).length);
        for (File file : files) {
            list.add(innerGet(file));
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        checkNull(files == null, "Directory read error");
        if (files != null) {
            for (File file : files) {
                innerDelete(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        checkNull(list == null, "Directory IO error");
        return Objects.requireNonNull(list).length;
    }

    protected void checkNull(boolean b, String s) {
        if (b) {
            throw new StorageException(s);
        }
    }
}
