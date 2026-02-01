package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage extends BasePage {

    private final By searchResults = By.cssSelector(".mw-search-result-heading a, .searchresult a, .mw-search-results li a");
    private final By noResultsMessage = By.cssSelector(".mw-search-nonefound, .mw-search-createlink");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasResults() {
        try {
            wait.until(driver -> {
                By articleTitle = By.cssSelector("#firstHeading, .mw-first-heading");
                return !driver.findElements(articleTitle).isEmpty() || 
                       !driver.findElements(searchResults).isEmpty() ||
                       isElementDisplayed(noResultsMessage);
            });
            By articleTitle = By.cssSelector("#firstHeading, .mw-first-heading");
            if (!driver.findElements(articleTitle).isEmpty()) {
                return true;
            }
            return !driver.findElements(searchResults).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public int getResultsCount() {
        List<WebElement> results = driver.findElements(searchResults);
        return results.size();
    }

    public ArticlePage clickFirstResult() {
        click(searchResults);
        return new ArticlePage(driver);
    }

    public boolean isNoResultsMessageDisplayed() {
        return isElementDisplayed(noResultsMessage);
    }
}
