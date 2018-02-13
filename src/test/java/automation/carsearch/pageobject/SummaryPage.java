package automation.carsearch.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SummaryPage extends  BasePage {


    public static final String REG_MARK = "reg-mark";
    public static final String VEHICLE_MAKE = "Vehicle make:";
    public static final String LIST_SUMMARY_ITEM = "list-summary-item";
    public static final String VEHICLE_COLOUR = "Vehicle colour:";
    public WebElement linkElement;
    public String carRegNo;
    public String color;
    public String model;
    private static final String LINK_TEXT_NAME="Search for another vehicle ";

    public SummaryPage(WebDriver driver) {
        this.driver = driver;
        carRegNo=getCarRegNo();
        model=getModel();
        color=getColor();


    }


    public SearchPage clickSearchForAnotherVehicle() throws InterruptedException {
        //((FirefoxDriver)driver).executeScript("document.getElementByClassName('button').scrollIntoView(true);");
        //linkElement.click();
        return new SearchPage(driver);
    }

    public String getCarRegNo() {
        return getElementByClassName(REG_MARK).getText();
    }



    public String getModel() {
        return getElementBySpanText(getElementsByClassName(LIST_SUMMARY_ITEM), VEHICLE_MAKE).getText();
    }



    public String getColor() {
        return getElementBySpanText(getElementsByClassName(LIST_SUMMARY_ITEM), VEHICLE_COLOUR).getText();
    }


}
