package com.mycar.service;

import com.mycar.model.CarVO;
import com.mycar.service.FileServiceImpl;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExcelReaderServiceTest {

    @Test
        public void testreadExcel()
        {
            List<CarVO> carviList = new ArrayList<>();
            try (DirectoryStream<Path> files = (Files.newDirectoryStream(Paths.get(new FileServiceImpl().getClass().getClassLoader()
                    .getResource("testfile2").toURI())))) {
                for(Path path: files)
                {
                    FileReaderService reader = new ExcelReaderService();
                    carviList = reader.readFile(path.toString());
                }
                assertEquals(2,carviList.size());
            } catch (IOException |URISyntaxException|InvalidFormatException e) {
                e.printStackTrace();
                fail();
            }

        }
}
