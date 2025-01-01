package tests.scrapeArticles;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import validations.scrapeArticles.ScrapeArticlesValidation;

import java.util.HashMap;

public class ScrapeArticlesTests {

    WebDriver driver;
    ScrapeArticlesValidation scrapeArticlesValidation;

    public ScrapeArticlesTests(WebDriver driver) {
        this.driver = DriverFactory.getDriver();
        this.scrapeArticlesValidation = new ScrapeArticlesValidation(driver);
    }


    public void iVisitTheHomePageAndClickOnAgreeButton() {

        scrapeArticlesValidation.visitHomePage();
        scrapeArticlesValidation.clickOnAgreeButton();
    }


    public void iClickOnTheSidebarToggleButtonAndNavigateToTheOpinionSection() {

        scrapeArticlesValidation.clickSidebarToggleButton();
    }


    public void iFetchAndVerifyTheFirstFiveArticlesAlongWithTheirContent() {

        scrapeArticlesValidation.navigateToOpinionSection();

    }
        public void iTranslateAllTheArticleTitlesToEnglishAndPrintThem() {

            scrapeArticlesValidation.translateAndPrintArticleTitles();
        }

        public void countRepeatedWordsInHeader()
        {
            scrapeArticlesValidation.printAllTitles();
            scrapeArticlesValidation.countRepeatedWords();
        }

    }
