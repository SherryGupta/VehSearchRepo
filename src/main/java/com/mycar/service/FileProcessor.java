package com.mycar.service;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface FileProcessor {
    String process(Path b) throws IOException;
}
