package validations.scrapeArticles.constants;

import org.openqa.selenium.By;

public interface ScrapeArticlesConstants {

    By SIDEBAR_TOGGLE_BUTTON = By.xpath("//button[@id='btn_open_hamburger']");

    String OPINION_SECTION = "//ul[@class='_ls']//a[normalize-space()='Opinión']";

    String ARTICLES_SECTION_XPATH = "//section[contains(@class,'_g _g-md _g-o b b-d')]/div/article";

    By AGREE_BUTTON =  By.xpath("//button[@id='didomi-notice-agree-button']");

    String CONTENT_SECTION_XPATH = "//div[@class='a_c clearfix']";

    String IMAGE_SECTION_XPATH = "//div[@class='a_e_m']/figure/span/img";
}
