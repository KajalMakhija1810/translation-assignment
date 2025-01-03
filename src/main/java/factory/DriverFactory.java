package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tl = new ThreadLocal<WebDriver>();
    public ChromeOptions options;

    public void initDriver(String browser, String browserVersion,String osVersion,String os,String deviceName, String deviceOrientation)  throws MalformedURLException  {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        String USERNAME = "kajalmakhija_u3G6lr";
        String KEY = "gqrza3PFLJLzkKxEKzcc";
        DesiredCapabilities caps = new DesiredCapabilities();

        if (!deviceName.equals("")){
            caps.setCapability("deviceName", deviceName);
        }
        caps.setCapability("browser",browser);
        caps.setCapability("os_version", osVersion);
       if(!browserVersion.equals("")){
           caps.setCapability("browser_version", browserVersion);
       }
        if (!os.equals("")){
            caps.setCapability("os", os);
        }

        if (!deviceOrientation.equals("")){
            caps.setCapability("deviceOrientation", deviceOrientation);
        }
        caps.setCapability("browserstack.user", "kajalmakhija_u3G6lr");
        caps.setCapability("browserstack.key", "gqrza3PFLJLzkKxEKzcc");
        caps.setCapability("browserstack.console", "info");

        tl.set(new RemoteWebDriver(new URL("https://" + USERNAME + ":" + KEY + "@hub.browserstack.com/wd/hub"),caps));
        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return tl.get();
    }
    }


//java -jar .\selenium-server.jar node --hub http://192.168.0.102:4444