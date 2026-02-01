package pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchScreen extends BasePage {

    private final By searchInput = By.id("org.wikipedia:id/search_src_text");
    private final By searchResultsList = By.id("org.wikipedia:id/search_results_list");
    private final By searchResultItem = By.id("org.wikipedia:id/page_list_item_title");
    private final By noResultsText = By.id("org.wikipedia:id/search_empty_view");

    public SearchScreen(AndroidDriver driver) {
        super(driver);
    }

    public SearchScreen searchFor(String query) {
        type(searchInput, query);
        wait.until(d -> isElementPresent(searchResultsList) || isElementPresent(noResultsText));
        return this;
    }

    public boolean areResultsDisplayed() {
        return isElementDisplayed(searchResultsList);
    }

    public int getResultsCount() {
        try {
            List<WebElement> results = driver.findElements(searchResultItem);
            return results.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public ArticleScreen clickFirstResult() {
        waitForElementClickable(searchResultItem).click();
        return new ArticleScreen(driver);
    }

    public boolean isNoResultsDisplayed() {
        return isElementDisplayed(noResultsText);
    }
}
