package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
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
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void innerSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        innerUpdate(resume, file);
    }

    @Override
    protected void innerDelete(File file) {
        if (file.exists()) {
            file.delete();
        } else {
            throw new StorageException("File does not exist", file.getName());
        }
    }

    @Override
    protected Resume innerGet(File file) {
        return fileToResume(file);
    }


    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> innerGetAllSorted() {
        Objects.requireNonNull(directory, "directory must not be null");
        List<Resume> listOfResumes = new ArrayList<>();
        for (File file : directory.listFiles()) {
            listOfResumes.add(fileToResume(file));
        }
        return listOfResumes;
    }

    @Override
    public void clear() {
        Objects.requireNonNull(directory, "directory must not be null");
        for (File file : directory.listFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        Objects.requireNonNull(directory, "directory must not be null");
        return directory.listFiles().length;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume fileToResume(File file);
}