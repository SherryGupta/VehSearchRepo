package com.mycar.service;

import com.mycar.model.CarVO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelReaderService implements FileReaderService{
    private final static Logger logger = Logger.getLogger(ExcelReaderService.class.getName());

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     * This function will a list of all cars stored in excel sheet. We are assuming that there is only one sheet in each excel sheet
     */
    @Override
    public List<CarVO> readFile(String fileName)throws IOException, InvalidFormatException{

        logger.log(Level.INFO,"fileName is ---"+fileName);

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(fileName));
        logger.log(Level.INFO,"Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        logger.log(Level.INFO,"\n\nIterating over Rows and Columns using Iterator\n");
        List<CarVO> carList = new ArrayList<>();
        for (Row row : sheet) {
            CarVO carVO = new CarVO();
            carVO.setRegNo(dataFormatter.formatCellValue(row.getCell(0)));
            carVO.setModel(dataFormatter.formatCellValue(row.getCell(1)));
            carVO.setColor(dataFormatter.formatCellValue(row.getCell(2)));
            carList.add(carVO);
        }
        logger.log(Level.INFO,"Car list size is "+carList.size());
        // Closing the workbook
        workbook.close();
        return carList;
    }
}
