package com.mycar.service;

import com.mycar.model.FileVO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

public interface FileService {

    Set<FileVO> getAllFilesInformation(String directoryName) throws IOException,URISyntaxException;

    Set<FileVO> filterFileonMimeType(String directoryName) throws IOException,URISyntaxException;

    Map<String,String> getAllFileMimeType(String directoryName) throws IOException,URISyntaxException;

}
