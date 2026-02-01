package pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ArticleScreen extends BasePage {

    private final By articleTitle = By.id("org.wikipedia:id/view_page_title_text");
    private final By articleToolbar = By.id("org.wikipedia:id/page_toolbar");
    private final By articleWebView = By.className("android.webkit.WebView");
    private final By articleContent = By.id("org.wikipedia:id/page_web_view");
    private final By backButton = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");
    private final By saveButton = By.id("org.wikipedia:id/page_save");
    private final By languageButton = By.id("org.wikipedia:id/page_language");
    private final By headerTitle = By.id("org.wikipedia:id/view_page_header_text");

    public ArticleScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isArticleDisplayed() {
        return isElementDisplayed(articleWebView) || isElementDisplayed(articleContent);
    }

    public String getArticleTitle() {
        if (isElementDisplayed(articleTitle)) {
            return getText(articleTitle);
        } else if (isElementDisplayed(headerTitle)) {
            return getText(headerTitle);
        }
        return "";
    }

    public boolean isTitleDisplayed() {
        return isElementDisplayed(articleTitle) || isElementDisplayed(headerTitle);
    }

    public void goBack() {
        click(backButton);
    }

    public boolean isToolbarDisplayed() {
        return isElementDisplayed(articleToolbar);
    }

    public void scrollToBottom() {
        for (int i = 0; i < 5; i++) {
            scrollDown();
        }
    }

    public void scrollToTop() {
        for (int i = 0; i < 5; i++) {
            scrollUp();
        }
    }

    public boolean isElementVisibleAfterScroll(String text) {
        By elementWithText = By.xpath("//*[contains(@text, '" + text + "')]");
        for (int i = 0; i < 10; i++) {
            if (isElementPresent(elementWithText)) {
                return true;
            }
            scrollDown();
        }
        return isElementPresent(elementWithText);
    }

    public boolean isSaveButtonDisplayed() {
        return isElementDisplayed(saveButton);
    }

    public boolean hasLanguageOption() {
        return isElementDisplayed(languageButton);
    }
}
