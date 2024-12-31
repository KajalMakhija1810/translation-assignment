package stepdefinitions.scrapeArticles;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import tests.scrapeArticlesTests.ScrapeArticlesTests;

public class ScrapeArticlesStepDefinitions
{
    WebDriver driver;
    ScrapeArticlesTests scrapeArticlesTests;

    public ScrapeArticlesStepDefinitions()
    {
        this.driver = DriverFactory.getDriver();
        scrapeArticlesTests = new ScrapeArticlesTests(driver);

    }

    @Given("I visit the home page and click on agree button")
    public void iVisitTheHomePage() {
            scrapeArticlesTests.visitHomePageAndClickOnAgreeButton();
    }

    @Then("I click on the sidebar toggle button and navigate to the opinion section")
    public void iClickOnTheSidebarToggleButtonAndNavigateToTheOpinionSection() {
    scrapeArticlesTests.clickOnSidebarToggleButtonAndNavigateToOpinionsSection();
    }

    @Then("I fetch the first five articles in Spanish")
    public void iFetchTheFirstFiveArticlesInSpanish() {

    }
}
