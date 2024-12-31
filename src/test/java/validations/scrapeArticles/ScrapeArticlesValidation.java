package validations.scrapeArticles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import validations.scrapeArticles.constants.ScrapeArticlesConstants;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class ScrapeArticlesValidation implements ScrapeArticlesConstants
{
    WebDriver driver;
    HashMap<String,String> articles;

    public ScrapeArticlesValidation(WebDriver driver) {
        this.driver = driver;
        articles = new HashMap<>();
    }

    public void visitHomePage()
    {
        driver.get("https://elpais.com/");

    }

    public void clickOnAgreeButton()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement agreeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AGREE_BUTTON));
        agreeButton.click();
    }


    public void clickSidebarToggleButton()
    {
        driver.findElement(SIDEBAR_TOGGLE_BUTTON).click();
    }

    public void navigateToOpinionSection(){
        driver.findElement(By.xpath(OPINION_SECTION)).click();
        List<WebElement> articles = driver.findElements(By.xpath(ARTICLES_SECTION_XPATH));

        for (int i = 0; i < articles.size(); i++) {
            WebElement article = articles.get(i);
            WebElement articleLink = article.findElement(By.xpath(".//header/h2/a"));
            String articleUrl = articleLink.getAttribute("href");
            String articleTitle = articleLink.getText();

            System.out.println("Article " + (i + 1) + ": " + articleUrl);
            openArticleInNewTabAndVerifyTheTitleAndExtractTitleAndContent(articleUrl,articleTitle);

        }

    }

    public  void openArticleInNewTabAndVerifyTheTitleAndExtractTitleAndContent(String url, String expectedTitle) {
        openNewTabWithUrl(url);

        String originalHandle = driver.getWindowHandle();
        String newTabHandle = switchToNewTab(originalHandle);

        String actualTitle = verifyTitle(expectedTitle);

        if (actualTitle != null) {
            System.out.println("Title: " + actualTitle);

            String articleContent = extractArticleContent();
            if (articleContent != null) {
                System.out.println("Article Content: " + articleContent);
                articles.put("Title: " + actualTitle, "Content: " + articleContent);
            } else {
                System.out.println("No content.");
            }
        }

        closeTabAndSwitchBack(originalHandle, newTabHandle);
    }

    public void openNewTabWithUrl(String url) {
        String script = "window.open('" + url + "', '_blank');";
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
    }

    public String switchToNewTab(String originalHandle) {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                return handle;
            }
        }
        return null;
    }

    public String verifyTitle(String expectedTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        String actualHeading = heading.getAttribute("textContent");

        if (actualHeading.equals(expectedTitle)) {
            return actualHeading;
        } else {
            System.out.println("Title mismatch. Expected: " + expectedTitle + " but got: " + actualHeading);
            return null;
        }
    }

    public String extractArticleContent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement content = null;
        try {
            content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONTENT_SECTION_XPATH)));
        } catch (Exception e) {
            System.out.println("No content found.");
            return null;
        }

        if (content != null && content.getText().trim().length() > 0) {
            return content.getText();
        }

        return null;
    }

    public void closeTabAndSwitchBack(String originalHandle, String newTabHandle) {
        driver.close();
        driver.switchTo().window(originalHandle);
    }











}
