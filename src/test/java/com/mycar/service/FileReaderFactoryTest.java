package com.mycar.service;

import com.mycar.exception.FileNotSupprtedException;
import com.mycar.model.FileVO;
import com.mycar.utility.Constants;
import org.junit.Test;


import static org.junit.Assert.*;

public class FileReaderFactoryTest {

    @Test
        public void testgetFileReaderforCSV()
        {
            FileVO fileVO =  new FileVO();
            fileVO.setMimeType(Constants.CSV_MIME);
            try {
                FileReaderService service = FileReaderFactory.getFileReader(fileVO);
                assertTrue(service instanceof CSVReaderService);
                assertFalse(service instanceof ExcelReaderService);
            } catch (FileNotSupprtedException e) {
                e.printStackTrace();
                fail();
            }


        }

    @Test
    public void testgetFileReaderforExcel()
    {
        FileVO fileVO =  new FileVO();
        fileVO.setMimeType(Constants.EXL_MIME);
        try {
            FileReaderService service = FileReaderFactory.getFileReader(fileVO);
            assertFalse(service instanceof CSVReaderService);
            assertTrue(service instanceof ExcelReaderService);
        } catch (FileNotSupprtedException e) {
            e.printStackTrace();
            fail();
        }


    }


}
