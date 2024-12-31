package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DriverUtils {
    public static void scrollElementToCenter(WebElement element, WebDriver driver) throws InterruptedException {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'})", element);
        Thread.sleep(3000);
    }
}
