package com.mycar.service;


import com.mycar.model.FileVO;
import org.apache.commons.io.FilenameUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import static com.mycar.utility.Constants.SUPPORTED_MIME_TYPE;
import static com.mycar.utility.FileUtility.getFileMime;
import java.util.function.Predicate;

public class FileServiceImpl implements FileService
{

    private final static Logger logger = Logger.getLogger(FileServiceImpl.class.getName());


    /**
     *
     * @param set<FileVO>
     * @param p <Predicate>
     * @return Set<FileVO>
     * @throws IOException,URISyntaxException
     * This is a static function using functional interface as one of its parameter. implementation of functional interface can be passed as an argument to this function
     * and it will filter all files in the specified directory accordingly.
     */

    public static Set<FileVO> filterFile(Set<FileVO> set ,Predicate<FileVO> p) throws IOException,URISyntaxException{
        logger.log(Level.INFO,"Calling method filterFile with file set size"+set.size());
        return  set.stream().filter(f->p.test(f)).collect(Collectors.toSet());
    }



    /**
     *
     * @param directoryName
     * @return Set<FileVO>
     * @throws IOException
     * This function will return a set of file objects. For each file in the directory it will create an file object.
     */
     public Set<FileVO> getAllFilesInformation(String directoryName) throws IOException,URISyntaxException {
         logger.log(Level.INFO,"Calling method getAllFilesInformation for directory" +directoryName);
         Set<FileVO> fileSet = new HashSet();
         try (DirectoryStream<Path> files = (Files.newDirectoryStream(Paths.get(new FileServiceImpl().getClass().getClassLoader()
                .getResource(directoryName).toURI())))) {

            for (Path path : files) {
                FileVO fileVO = populateFileVO(path);
                fileSet.add(fileVO);
            }
            logger.log(Level.INFO, "FileVO size is" + fileSet.size());
            return fileSet;

        }
    }


    /**
     *
     * @param directoryName
     * @return Set<FileVO>
     * @throws IOException,URISyntaxException
     * This function will return set of all FileVos, for system supported mime types. All supported mime types are definded in Constants class.
     */

    @Override
    public Set<FileVO> filterFileonMimeType(String directoryName) throws IOException,URISyntaxException {
        logger.log(Level.INFO,"Calling method filterFileonMimeType for directory" +directoryName);
        Predicate<FileVO> fileVOPredicate = fileVO -> Arrays.asList(SUPPORTED_MIME_TYPE).contains(fileVO.getMimeType().toLowerCase());
        Set <FileVO> fileSet = getAllFilesInformation(directoryName);
        logger.log(Level.INFO, "filtered FileVOs size is" + fileSet.size());
        return filterFile(fileSet,fileVOPredicate);
    };


    /**
     *
     * @param directoryName
     * @param p (Functional Interface
     * @return Map of all files (file name and requested file attribute) in the specified directory, based on functional parameters passed.
     * @throws IOException,URISyntaxException
     * This function is working on the basis of functional interface FileProcessor. This can be used to collect some specific property of all files
     * in the specified directory like file extension of file mime type etc..
     */

    public static Map<String,String> processAllFile(String directoryName ,FileProcessor p) throws IOException,URISyntaxException {
        logger.log(Level.INFO,"Calling method processAllFile for directory" +directoryName);
        try (DirectoryStream<Path> files = (Files.newDirectoryStream(Paths.get(new FileServiceImpl().getClass().getClassLoader()
                .getResource(directoryName).toURI())))) {

            Map<String, String> fileMap = fileMap = new HashMap<>();
            for (Path file : files) {
                fileMap.put(file.getFileName().toString(), p.process(file));
            }
            logger.log(Level.INFO, "Map size is" + fileMap.size());
            return fileMap;
        }
    }


    /**
     *
     * @param directoryName
     * @return Returning a map of file name and mimetype of each files in the specified directory
     * @throws IOException,URISyntaxException
     * This function is returning mime type of all files in the specified directory
     */

    @Override
    public Map<String,String> getAllFileMimeType(String directoryName) throws IOException,URISyntaxException {
        logger.log(Level.INFO,"Calling method getAllFileMimeType for directory" +directoryName);
        return processAllFile(directoryName,(Path path )-> getFileMime(path.toString()));
    }



    /**
     *
     * @param path
     * @return Populating FileVO
     */
    public static FileVO populateFileVO(Path path) throws IOException {
        logger.log(Level.INFO,"Calling method populateFileVO for path" + path.toString());
        FileVO fileVO = new FileVO();
        fileVO.setFileName(path.getFileName().toString());
        fileVO.setFileExt(FilenameUtils.getExtension(path.getFileName().toString()));
        fileVO.setFileSize(path.toFile().length());
        fileVO.setMimeType(getFileMime(path.toString()));
        fileVO.setFilePath(path.toString());
        return fileVO;
    }



}
