package stepdefinitions.hooks;


import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.testng.Reporter;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;




import java.net.MalformedURLException;

public class ApplicationHooks {

    DriverFactory driverFactory = new DriverFactory();

    @Before
    public void openBrowser() throws MalformedURLException {
        System.out.println("inside hooks");
        String browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
        String browserVersion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browserVersion");
        String os = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("os");
        String osVersion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("osVersion");
        String deviceName = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("deviceName");
        String deviceOrientation = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("deviceOrientation");
        driverFactory.initDriver(browser, browserVersion, osVersion,os,deviceName, deviceOrientation);
    }

    @After
    public void tearDown(){
//        DriverFactory1.getDriver().quit();
    }

}
