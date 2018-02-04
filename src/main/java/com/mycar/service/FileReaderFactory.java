package com.mycar.service;

import com.mycar.exception.FileNotSupprtedException;
import com.mycar.model.FileVO;

public class FileReaderFactory {

    /**
     *
     * @param fileVO
     * @return FileReaderService
     * We are assuming that our system is supprting only excel and csv file. This method will return
     * an instance of CSVReaderService or ExcelReaderService depending of file mime type
     *
     */
    public static FileReaderService getFileReader(FileVO fileVO) throws FileNotSupprtedException
    {
        if(fileVO.isCSV())
            return new CSVReaderService();
        else if (fileVO.isEXL()){
            return new ExcelReaderService();
        } else
            throw new FileNotSupprtedException("This file format is not supported");
    }

}
