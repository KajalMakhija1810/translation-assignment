package stepdefinitions.hooks;


import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;




import java.net.MalformedURLException;

public class ApplicationHooks {

    DriverFactory driverFactory = new DriverFactory();

    @Before
    public void openBrowser() throws MalformedURLException {
        System.out.println("inside hooks");
        driverFactory.initDriver();
    }

    @After
    public void tearDown(){
//        DriverFactory1.getDriver().quit();
    }

}
