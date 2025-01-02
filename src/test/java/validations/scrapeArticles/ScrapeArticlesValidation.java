package validations.scrapeArticles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import validations.scrapeArticles.constants.ScrapeArticlesConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrapeArticlesValidation implements ScrapeArticlesConstants {

    WebDriver driver;
    HashMap<String, String> articles;
    HashMap<Integer, String> translatedArticles;

    public ScrapeArticlesValidation(WebDriver driver) {
        this.driver = driver;
        articles = new HashMap<>();
        this.translatedArticles = new HashMap<>();
    }

    public void visitHomePage() {
        driver.get("https://elpais.com/");
    }

    public void clickOnAgreeButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement agreeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AGREE_BUTTON));
        agreeButton.click();
    }

    public void clickSidebarToggleButton() {
        driver.findElement(SIDEBAR_TOGGLE_BUTTON).click();
    }

    public void navigateToOpinionSection() {
        driver.findElement(By.xpath(OPINION_SECTION)).click();
        List<WebElement> articlesList = driver.findElements(By.xpath(ARTICLES_SECTION_XPATH));

        for (int i = 0; i < Math.min(5, articlesList.size()); i++) {
            WebElement article = articlesList.get(i);
            WebElement articleLink = article.findElement(By.xpath(".//header/h2/a"));
            String articleUrl = articleLink.getAttribute("href");
            String articleTitle = articleLink.getText();

            System.out.println("Article " + (i + 1) + ": " + articleUrl);
            openArticleInNewTabAndVerifyTitleAndExtractContent(articleUrl, articleTitle);
        }
    }

    public void openArticleInNewTabAndVerifyTitleAndExtractContent(String url, String expectedTitle) {
        openNewTabWithUrl(url);

        String originalHandle = driver.getWindowHandle();
        String newTabHandle = switchToNewTab(originalHandle);

        String actualTitle = verifyTitle(expectedTitle);

        if (actualTitle != null) {
            System.out.println("Title: " + actualTitle);

            String articleContent = extractArticleContent();

            if (articleContent != null) {
                System.out.println("Article Content: " + articleContent);
                articles.put( actualTitle, articleContent);
            } else {
                System.out.println("No content found for this article.");
                articles.put( actualTitle, "");
            }

        }

        downloadCoverImage(actualTitle);

        closeTabAndSwitchBack(originalHandle, newTabHandle);
    }
    public void downloadCoverImage(String articleTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {

            WebElement coverImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IMAGE_SECTION_XPATH)));


            String imageUrl = coverImage.getAttribute("src");

            if (imageUrl != null && !imageUrl.isEmpty()) {
                System.out.println("Image URL: " + imageUrl);


                String safeArticleTitle = articleTitle.replaceAll("[^a-zA-Z0-9]", "_"); // Replace unsafe characters with underscores


                downloadImage(imageUrl, safeArticleTitle + ".jpg");

            } else {
                System.out.println("Cover image not found.");
            }
        } catch (Exception e) {
            System.out.println("Error downloading cover image: " + e.getMessage());
        }
    }
    public void downloadImage(String imageUrl, String fileName) {
        try {

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);


            InputStream inputStream = connection.getInputStream();


            FileOutputStream outputStream = new FileOutputStream(new File(fileName));


            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close the streams
            outputStream.close();
            inputStream.close();

            System.out.println("Image downloaded successfully: " + fileName);

        } catch (IOException e) {
            System.out.println("Error downloading image: " + e.getMessage());
        }
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
            return content.getAttribute("textContent");
        }

        return null;
    }

    public void closeTabAndSwitchBack(String originalHandle, String newTabHandle) {
        driver.close();
        driver.switchTo().window(originalHandle);
    }

    public void translateAndPrintArticleTitles() {
        Integer articleNo = 1;
        for (String articleTitle : articles.keySet()) {
            String translatedTitle = translateTextToEnglish(articleTitle);
            System.out.println("Translated Title: " + translatedTitle);
            translatedArticles.put(articleNo, translatedTitle);
            articleNo++;

        }
    }

    public String translateTextToEnglish(String text) {
        try {
            String apiUrl = "https://rapid-translate-multi-traduction.p.rapidapi.com/t";
            String requestBody = String.format("{\"from\":\"es\",\"to\":\"en\",\"q\":\"%s\"}", text);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("x-rapidapi-key", "a346cd2159msh53a66a7cfbc1db6p1448d4jsn226b25afd22c")
                    .header("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return parseTranslationResponse(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String parseTranslationResponse(String responseBody) {
        String translatedText = null;
        try {
            translatedText = responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return translatedText;
    }

    public void printAllTitles() {
        System.out.println("All Translated Titles:");
        for (Map.Entry<Integer, String> entry : translatedArticles.entrySet()) {
            System.out.println("Article " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public void countRepeatedWords() {
        HashMap<String, Integer> wordCount = new HashMap<>();

        StringBuilder allTitles = new StringBuilder();
        for (String title : translatedArticles.values()) {
            allTitles.append(title).append(" ");
        }
        System.out.println("All Titles Combined:\n" + allTitles.toString());

        String[] words = allTitles.toString().split("\\W+");  // \\W+ matches any non-word characters

        for (String word : words) {
            if (!word.isEmpty()) {
                word = word.toLowerCase();
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        
        System.out.println("Repeated Words (appearing more than twice):");
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println("Word: '" + entry.getKey() + "' - Count: " + entry.getValue());
            }
        }
    }


}

