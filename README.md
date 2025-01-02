
# Scrape Articles and Download Images

This project is a web scraping automation tool that performs the following tasks:
1. Navigates to a website (`elpais.com`).
2. Clicks through the necessary pop-ups (like cookie agreement).
3. Scrapes articles from the opinion section of the website.
4. Opens each article, extracts its content, and downloads its cover image with the article title as the filename.
5. Translates the article titles from Spanish to English using an external API.
6. Prints the translated titles and counts repeated words across all titles.

## Prerequisites

Before running the project, make sure you have the following installed:

- **Java 11 or higher**.
- **Selenium WebDriver**: Ensure you have the proper driver (e.g., ChromeDriver) for your browser installed.
- **Internet Access**: The project makes HTTP requests to download images and call an external API for translation.

You will also need to install the necessary dependencies:

- **Selenium WebDriver** (for web automation).
- **HttpClient** (for making HTTP requests).

## Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/scrape-articles.git
    cd scrape-articles
    ```

2. Download the WebDriver for your browser:
    - [ChromeDriver](https://sites.google.com/chromium.org/driver/)

3. Set up your preferred Java IDE (IntelliJ IDEA, Eclipse, etc.) or use the command line.

4. Make sure to include Selenium in your project. You can add the dependency in your `pom.xml` if you are using Maven:

    ```xml
    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.0.0</version>
        </dependency>
    </dependencies>
    ```

5. Add the RapidAPI key to the `translateTextToEnglish` method.

    Replace the placeholder in the API header:

    ```java
    .header("x-rapidapi-key", "YOUR_RAPIDAPI_KEY")
    ```

    You can obtain a free RapidAPI key by signing up [here](https://rapidapi.com/).


1. **Initialize the WebDriver**:

    The project uses Selenium WebDriver to automate interactions with the website. Create a `WebDriver` instance for the browser you are using (e.g., Chrome).

    Example for initializing Chrome:

    ```java
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
    WebDriver driver = new ChromeDriver();
    ```

2. **Run the main scraping process**:

    Create an instance of `ScrapeArticlesValidation` and call the `visitHomePage` and `navigateToOpinionSection` methods.

    Example usage:

    ```java
    ScrapeArticlesValidation scraper = new ScrapeArticlesValidation(driver);
    scraper.visitHomePage();
    scraper.clickOnAgreeButton();
    scraper.navigateToOpinionSection();
    ```

3. **Downloading Images**:
    The cover image of each article will be downloaded to the current directory using the article title as the filename.

4. **Translation and Word Count**:
    After scraping the articles, the article titles are translated to English, and repeated words across all titles are counted.

    To print the translated titles and count repeated words:

    ```java
    scraper.translateAndPrintArticleTitles();
    scraper.countRepeatedWords();
    ```

## Example Output

After scraping and processing, the output might look something like this:


Article 1: https://elpais.com/opinion/2025/01/01/some-article-title
Title: Some Article Title
Article Content: The content of the article...
Image URL: https://elpais.com/path/to/image.jpg
Image downloaded successfully: Some_Article_Title.jpg

Translated Title: Some Article Title (English)
Translated Title: Another Article Title (English)

Repeated Words (appearing more than twice):
Word: 'the' - Count: 3
```

## Notes

- Ensure the **WebDriver** path is correctly set for the browser you're using.
- If the structure of the website changes, you might need to adjust the XPath expressions in the `navigateToOpinionSection`, `extractArticleContent`, and `downloadCoverImage` methods.
- The project uses the `rapid-translate-multi-traduction.p.rapidapi.com` API for translating titles. Make sure to configure your RapidAPI key in the `translateTextToEnglish` method.
 
