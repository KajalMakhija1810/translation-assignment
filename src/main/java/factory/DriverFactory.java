package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tl = new ThreadLocal<WebDriver>();
    public ChromeOptions options;

    public void initDriver()  throws MalformedURLException  {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        tl.set(new ChromeDriver(options));
        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return tl.get();
    }
    }


//java -jar .\selenium-server.jar node --hub http://192.168.0.102:4444