package automation.carsearch.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage extends  BasePage {


    public WebElement carRegNumberTextBox;
    public WebElement continueButton;
    private static final String VRM_TEXT_BOX_ID="Vrm";
    private static final String CONTINUE_BUTTON_CLASS="button";


    public SearchPage(WebDriver driver) {
        this.driver = driver;
        carRegNumberTextBox= getElementById(VRM_TEXT_BOX_ID);
        continueButton= getElementByClassName(CONTINUE_BUTTON_CLASS);

    }


    public ConfirmationPage enterRegNoClickContinue(String CarRegNo) throws InterruptedException {
        carRegNumberTextBox.clear();
        carRegNumberTextBox.sendKeys(CarRegNo);
        continueButton.click();
        return new ConfirmationPage(driver);
    }

}