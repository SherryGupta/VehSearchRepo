package com.mycar.service;

import com.mycar.model.CarVO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface FileReaderService {
    public List<CarVO> carList = new ArrayList<>();

    List<CarVO> readFile(String fileName)throws IOException, InvalidFormatException;
}
