package selenium;

import com.mycar.exception.FileNotSupprtedException;
import com.mycar.model.CarVO;
import com.mycar.model.FileVO;
import com.mycar.service.FileReaderFactory;
import com.mycar.service.FileService;
import com.mycar.service.FileServiceImpl;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DVLASiteVehicleTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://www.gov.uk/get-vehicle-information-from-dvla";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    @Test
    public void testgetFileMime()
    {
        FileService fileServiceImpl = new FileServiceImpl();
        List<CarVO> inputList = new ArrayList<CarVO>();
        try {
            Set<FileVO> files = fileServiceImpl.filterFileonMimeType("files");
            for(FileVO filevo: files) {
                inputList= FileReaderFactory.getFileReader(filevo).readFile(filevo.getFilePath());

                for (CarVO carVO : inputList) {

                    driver.navigate().to(baseUrl);
                    driver.findElement(By.linkText("Start now")).click();
                    driver.findElement(By.id("Vrm")).clear();
                    driver.findElement(By.id("Vrm")).sendKeys(carVO.getRegNo());
                    driver.findElement(By.name("Continue")).click();
                    driver.findElement(By.id("Correct_True")).click();
                    driver.findElement(By.name("Continue")).click();
                    String regName = driver.findElement(By.className("reg-mark")).getText();
                    assertEquals(carVO.getRegNo(), regName);
                    assertTrue(driver.getPageSource().contains(carVO.getColor()));
                    assertTrue(driver.getPageSource().contains(carVO.getModel()));
                    driver.findElement(By.linkText("Search for another vehicle")).click();


                }
            }
        } catch (IOException|URISyntaxException |FileNotSupprtedException|InvalidFormatException e) {
             e.printStackTrace();
        }
    }



}