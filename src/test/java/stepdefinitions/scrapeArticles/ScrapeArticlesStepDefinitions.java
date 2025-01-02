package stepdefinitions.scrapeArticles;

import factory.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import tests.scrapeArticles.ScrapeArticlesTests;

import java.util.HashMap;

public class ScrapeArticlesStepDefinitions {

    WebDriver driver;
    tests.scrapeArticles.ScrapeArticlesTests scrapeArticlesTests;

    public ScrapeArticlesStepDefinitions() {
        this.driver = DriverFactory.getDriver();
        this.scrapeArticlesTests = new ScrapeArticlesTests(driver);
    }

    @Given("I visit the home page and click on agree button")
    public void iVisitTheHomePageAndClickOnAgreeButton() {

        scrapeArticlesTests.iVisitTheHomePageAndClickOnAgreeButton();
    }

    @Given("I click on the sidebar toggle button and navigate to the opinion section")
    public void iClickOnTheSidebarToggleButtonAndNavigateToTheOpinionSection() {
        scrapeArticlesTests.iClickOnTheSidebarToggleButtonAndNavigateToTheOpinionSection();
    }

    @Then("I fetch and verify the first five articles along with their content and download cover image if present")
    public void iFetchAndVerifyTheFirstFiveArticlesAlongWithTheirContent() {

        scrapeArticlesTests.iFetchAndVerifyTheFirstFiveArticlesAlongWithTheirContent();
    }

    @And("I translate all the article titles to English and print them")
    public void iTranslateAllTheArticleTitlesToEnglishAndPrintThem() {

       scrapeArticlesTests.iTranslateAllTheArticleTitlesToEnglishAndPrintThem();
    }

    @And("Count the repeated words whose count is greater than 2")
    public void countTheRepeatedWordsWhoseCountIsGreaterThan2() {
        scrapeArticlesTests.countRepeatedWordsInHeader();
    }


}
