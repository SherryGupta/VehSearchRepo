package com.mycar.service;

import com.mycar.model.CarVO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CSVReaderService  implements FileReaderService{
    private final static Logger logger = Logger.getLogger(CSVReaderService.class.getName());

    private Function<String, CarVO> mapToItem = (line) -> {
        String[] p = line.split(",");
        CarVO carVO = new CarVO();
        carVO.setRegNo(p[0]);
        carVO.setModel(p[1]);
        carVO.setColor(p[2]);
        return carVO;
    };

    /**
     *
     * @param fileName
     * @return
     * @throws IOException,InvalidFormatException
     * @throws InvalidFormatException
     * This function will return a list of all cars stored in csv files.
     */
    @Override
    public List<CarVO> readFile(String fileName)throws IOException, InvalidFormatException{
        logger.log(Level.INFO,"fileName is ---"+fileName);
        File inputF = new File(fileName);
        InputStream inputFS = new FileInputStream(inputF);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
        List<CarVO> carVOS = br.lines().map(mapToItem).collect(Collectors.toList());
        logger.log(Level.INFO,"Car List Size is ---"+carVOS.size());
        return carVOS;
    }
}
