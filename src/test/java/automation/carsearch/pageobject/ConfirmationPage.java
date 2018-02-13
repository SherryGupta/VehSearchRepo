package automation.carsearch.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmationPage extends  BasePage {


    public WebElement yesRadioButton;
    public WebElement continueButton;
     private static final String YES_RADIO_BUTTON_ID="Correct_True";
    private static final String CONTINUE_BUTTON_CLASS="button";

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        yesRadioButton= getElementById(YES_RADIO_BUTTON_ID);
        continueButton= getElementByClassName(CONTINUE_BUTTON_CLASS);

    }


    public SummaryPage selectYesAndClickContinue() throws InterruptedException {
        yesRadioButton.click();
        continueButton.click();
        return new SummaryPage(driver);
    }
}