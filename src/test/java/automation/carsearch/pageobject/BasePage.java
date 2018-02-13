package automation.carsearch.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BasePage {

    protected WebDriver driver;
    public WebElement getElementById(String id)
    {
        return driver.findElement(By.id(id));
    }

    public WebElement getElementByClassName(String className)
    {
        return driver.findElement(By.className(className));
    }

    public WebElement getElementBylink(String linkText)
    {
        return driver.findElement(By.linkText(linkText));
    }

    public List<WebElement> getElementsByClassName(String className)
    {
        return driver.findElements(By.className(className));
    }

    public WebElement getElementBySpanText(List<WebElement> listElement, String spanText)
    {

        for(WebElement element:listElement)
        {
            List<WebElement> spanList = element.findElements(By.tagName("span"));
            WebElement firstSpan =     (WebElement)spanList.get(0);
            if(firstSpan.getText().equalsIgnoreCase(spanText))
                return (WebElement)spanList.get(1);
        }
        return null;
    }

}
