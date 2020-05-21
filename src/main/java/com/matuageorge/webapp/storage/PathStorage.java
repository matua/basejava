package com.matuageorge.webapp.storage;

import com.matuageorge.webapp.exception.StorageException;
import com.matuageorge.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final ObjectstreamSerialization objectstreamSerialization;

    protected PathStorage(String dir, ObjectstreamSerialization objectstreamSerialization) {
        directory = Paths.get(dir);
        this.objectstreamSerialization = objectstreamSerialization;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writable");
        }
    }

    @Override
    protected Path getKey(String uuid) {
        return new File(directory.toFile(), uuid).toPath();
    }

    @Override
    protected void innerUpdate(Resume resume, Path path) {
        try {
            objectstreamSerialization.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path update error", resume.getUuid(), e);
        }
    }

    @Override
    protected void innerSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Path create error", resume.getUuid(), e);
        }
        innerUpdate(resume, path);
    }

    @Override
    protected void innerDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("File does not exist", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume innerGet(Path path) {
        try {
            return objectstreamSerialization.fileToResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> innerGetAllSorted() {
        try {
            return Files.list(directory)
                    .map(this::innerGet)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::innerDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }
}
