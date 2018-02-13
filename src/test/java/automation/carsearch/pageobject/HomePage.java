package automation.carsearch.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends  BasePage {


    public WebElement starNowElement;
    private static final String START_NOW_BUTTON_CLASS="button";

    public HomePage(WebDriver driver)
    {
        this.driver=driver;
        starNowElement= getElementByClassName(START_NOW_BUTTON_CLASS);

    }


    public SearchPage clickStartNow() throws InterruptedException {
        starNowElement.click();
        return new SearchPage(driver);
    }




}
