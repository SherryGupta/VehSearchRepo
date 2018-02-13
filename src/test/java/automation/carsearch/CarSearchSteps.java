package automation.carsearch;

import automation.carsearch.pageobject.ConfirmationPage;
import automation.carsearch.pageobject.HomePage;
import automation.carsearch.pageobject.SearchPage;
import automation.carsearch.pageobject.SummaryPage;
import com.mycar.model.CarVO;
import com.mycar.model.FileVO;
import com.mycar.service.FileReaderFactory;
import com.mycar.service.FileService;
import com.mycar.service.FileServiceImpl;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import  cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class CarSearchSteps {

    public static final String HTTPS_VEHICLEENQUIRY_SERVICE_GOV_UK = "https://vehicleenquiry.service.gov.uk";
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private List<CarVO> inputList = new ArrayList<CarVO>();
    private List<CarVO> carDetailsFromDvlaList = new ArrayList<CarVO>();
    private List<String> objs = new ArrayList();
    private CarVO car;
    private HomePage homePage;
    private SearchPage searchPage;
    private ConfirmationPage confirmationPage;
    private SummaryPage summaryPage;


    @Before
    public void before() {
        driver = new FirefoxDriver();
        baseUrl  = "https://www.gov.uk/get-vehicle-information-from-dvla";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void after() {
        driver.quit();
    }

    @Given("^csv/xsl files with car details exists in folder$")
    public void csvFileWithCarDetailsExistsInFolder() {
        System.out.println("files are existing");
    }


    @When("^service is invoked to read the file$")
    public void serviceIsInvokedToReadTheFile() throws Throwable {

        FileService fileServiceImpl = new FileServiceImpl();
        try {
            Set<FileVO> files = fileServiceImpl.filterFileonMimeType("files");
            for (FileVO filevo : files) {
                inputList = FileReaderFactory.getFileReader(filevo).readFile(filevo.getFilePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @When("^user navigates to DVLA site$")
    public void userNavigatesToDVLASite() throws Throwable {
        driver.navigate().to(baseUrl);
        homePage = new HomePage(driver);
        searchPage = homePage.clickStartNow();
     }

    /**
     * ~This method will store car details from dvla site for each car reg number stored in csv/xls files in a java collection
     *
     */

    @When("^search for cars by car reg number received in from service$")
    public void searchForCarsByCaRegNumberReceivedInFromService() throws InterruptedException {
        for (CarVO carvo : inputList)
        {
            confirmationPage = searchPage.enterRegNoClickContinue(carvo.getRegNo());
            summaryPage = confirmationPage.selectYesAndClickContinue();
            CarVO car =new CarVO();
            car.setColor(summaryPage.color);
            car.setModel(summaryPage.model);
            car.setRegNo(summaryPage.carRegNo);
            carDetailsFromDvlaList.add(car);
            driver.navigate().to(baseUrl);
            homePage = new HomePage(driver);
            searchPage = homePage.clickStartNow();

        }


    }

    /**
     * this method will compare some of expected card details with the collection of car details received from dvla site
     *
     */
    @Then("^cars details displayed on the website and matched with expected car details$")
    public void carsDetailsDisplayedOnTheWebsite(DataTable table) {

        List<CarVO> expectedCarVO =new ArrayList();
        for (List<String> row : table.raw()) {
            CarVO car = new CarVO();
            car.setRegNo(row.get(0));
            car.setColor(row.get(2));
            car.setModel(row.get(1));
            expectedCarVO.add(car);
        }

        for(CarVO expectedCar:expectedCarVO)
        {
            int counter=0;
            for(CarVO carActual:carDetailsFromDvlaList)
            {
                if(carActual.getColor().equalsIgnoreCase(expectedCar.getColor()) && carActual.getRegNo().equalsIgnoreCase(expectedCar.getRegNo()) && carActual.getModel().equalsIgnoreCase(expectedCar.getModel()))
                    counter=counter+1;
                if(counter==1)
                    break;
            }

            Assert.assertTrue("car record matched",counter==1);
            Assert.assertFalse("car record not matched",counter!=1);
        }



    }



}
