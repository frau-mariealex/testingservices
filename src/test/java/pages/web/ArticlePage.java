package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ArticlePage extends BasePage {

    private final By articleTitle = By.cssSelector("#firstHeading, .mw-first-heading, h1.firstHeading");
    private final By articleContent = By.cssSelector("#mw-content-text, .mw-body-content, #bodyContent");
    private final By languageLinks = By.cssSelector("#p-lang .interlanguage-link a, .interlanguage-link a, a.interlanguage-link-target");
    private final By languageButton = By.cssSelector("#p-lang-btn, .mw-interlanguage-selector, button[aria-haspopup='true']");

    public ArticlePage(WebDriver driver) {
        super(driver);
    }

    public String getArticleTitle() {
        return getText(articleTitle);
    }

    public boolean isArticleTitleDisplayed() {
        return isElementDisplayed(articleTitle);
    }

    public boolean isArticleContentDisplayed() {
        return isElementDisplayed(articleContent);
    }

    public int getLanguageLinksCount() {
        // Попробовать открыть меню языков если оно скрыто
        if (isElementDisplayed(languageButton)) {
            try {
                click(languageButton);
                wait.until(ExpectedConditions.visibilityOfElementLocated(languageLinks));
            } catch (Exception ignored) {}
        }
        List<WebElement> links = driver.findElements(languageLinks);
        return links.size();
    }

    public ArticlePage switchToLanguage(String languageCode) {
        // Открыть меню языков
        if (isElementDisplayed(languageButton)) {
            click(languageButton);
        }
        
        By langLink = By.cssSelector("a[hreflang='" + languageCode + "'], a[lang='" + languageCode + "'], a[href*='" + languageCode + ".wikipedia.org']");
        wait.until(ExpectedConditions.elementToBeClickable(langLink));
        click(langLink);
        
        wait.until(ExpectedConditions.urlContains(languageCode + ".wikipedia.org"));
        return new ArticlePage(driver);
    }

    public String getCurrentLanguage() {
        String url = getCurrentUrl();
        if (url.contains(".wikipedia.org")) {
            return url.split("//")[1].split("\\.")[0];
        }
        return "unknown";
    }

    public boolean urlContains(String text) {
        return getCurrentUrl().contains(text);
    }
}
