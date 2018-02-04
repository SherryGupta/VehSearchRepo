package com.mycar.service;

import com.mycar.model.FileVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class FileServiceImplTest {


    @Test
    public void testprocessAllFile()
    {
        try
        {
        FileProcessor processor = (Path path )-> String.valueOf(path.toFile().length());
        Map<String, String> stringStringMap = FileServiceImpl.processAllFile("files", processor);
            assertEquals(12, stringStringMap.size());
        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    public void testfilterFileonMimeType()
    {
        FileServiceImpl fileServiceImpl = new FileServiceImpl();
        try
        {
            Set<FileVO> filterFileonMimeType = fileServiceImpl.filterFileonMimeType("files");
            assertEquals(10,filterFileonMimeType.size());
        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testGetAllFilesInformation()
    {
        FileServiceImpl fileServiceImpl = new FileServiceImpl();
        try
        {
            Set<FileVO> filterFileonMimeTypeSet = fileServiceImpl.getAllFilesInformation("testfiles");
            assertEquals(1,filterFileonMimeTypeSet.size());
            for (FileVO file :filterFileonMimeTypeSet ) {
                assertEquals("csv",file.getFileExt());
                assertEquals("text/csv",file.getMimeType());
                assertEquals("car1.csv",file.getFileName());
                assertEquals(42,file.getFileSize());
            }
        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testfilterFile()
    {
        try
        {
            Set<FileVO> FileVOSet = new HashSet<>();
            FileVO file = new FileVO();
            file.setFileExt("txt");
            FileVOSet.add(file);

            FileVO file2 =new FileVO();
            file2.setFileExt("csv");
            FileVOSet.add(file2);

            FileVO file3 =new FileVO();
            file3.setFileExt("txt");
            FileVOSet.add(file3);

            Predicate<FileVO> fileVOPredicate = fileVO -> "txt".equalsIgnoreCase(fileVO.getFileExt());
            Predicate<FileVO> fileVOPredicate1 = fileVO -> "csv".equalsIgnoreCase(fileVO.getFileExt());

            Set<FileVO> filterestSet = FileServiceImpl.filterFile(FileVOSet,fileVOPredicate);
            Set<FileVO> filterestSet1 = FileServiceImpl.filterFile(FileVOSet,fileVOPredicate1);
            assertEquals(2,filterestSet.size());
            assertEquals(1,filterestSet1.size());
        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    public void testPopulateFile()
    {
        FileServiceImpl fileServiceImpl = new FileServiceImpl();
        FileVO filevo = null;
        try (DirectoryStream<Path> files = (Files.newDirectoryStream(Paths.get(new FileServiceImpl().getClass().getClassLoader()
                .getResource("testfiles").toURI())))) {

            for (Path file : files) {
                filevo = FileServiceImpl.populateFileVO(file);
                assertEquals("csv",filevo.getFileExt());
                assertEquals("text/csv",filevo.getMimeType());
                assertEquals("car1.csv",filevo.getFileName());
                assertEquals(42,filevo.getFileSize());
            }

        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            fail();
        }
     }




    @Test
    public void testGetFileMime()
    {
        FileServiceImpl fileServiceImpl = new FileServiceImpl();
        try
        {
            Map<String,String> mimeMaps = fileServiceImpl.getAllFileMimeType("testfiles1");
            assertEquals(2,mimeMaps.size());
            assertTrue(mimeMaps.containsValue("text/csv"));
            assertTrue(mimeMaps.containsValue("application/vnd.ms-excel"));

        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}