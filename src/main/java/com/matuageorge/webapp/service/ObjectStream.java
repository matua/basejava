package com.matuageorge.webapp.service;

import com.matuageorge.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public interface ObjectStream {
    void doWrite(Resume resume, BufferedOutputStream os) throws IOException;

    Resume fileToResume(BufferedInputStream is) throws IOException;
}
