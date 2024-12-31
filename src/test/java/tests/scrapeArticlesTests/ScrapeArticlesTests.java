package tests.scrapeArticlesTests;

import org.openqa.selenium.WebDriver;
import validations.scrapeArticles.ScrapeArticlesValidation;

public class ScrapeArticlesTests {

    WebDriver driver;
    ScrapeArticlesValidation scrapeArticlesValidation;
    public ScrapeArticlesTests(WebDriver driver) {
        this.driver = driver;
        scrapeArticlesValidation = new ScrapeArticlesValidation(driver);
    }
    public void visitHomePageAndClickOnAgreeButton()
    {
        scrapeArticlesValidation.visitHomePage();
        scrapeArticlesValidation.clickOnAgreeButton();

    }

    public void clickOnSidebarToggleButtonAndNavigateToOpinionsSection()
    {
        scrapeArticlesValidation.clickSidebarToggleButton();
        scrapeArticlesValidation.navigateToOpinionSection();
    }




}
