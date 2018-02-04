package com.mycar.utility;

import static org.junit.Assert.*;
import com.mycar.service.FileServiceImpl;
import org.junit.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileUtilityTest {

    @Test
    public void testgetFileMime()
    {

        try (DirectoryStream<Path> files = (Files.newDirectoryStream(Paths.get(new FileServiceImpl().getClass().getClassLoader()
                .getResource("testfiles").toURI())))) {
            for(Path path: files)
            {
                assertEquals("text/csv",FileUtility.getFileMime(path.toString()));
            }

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            fail();
        }

    }

}