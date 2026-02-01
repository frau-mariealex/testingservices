package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private final By searchInput = By.cssSelector("#searchInput, input[name='search'], .cdx-text-input__input");
    private final By logo = By.cssSelector(".mw-logo, img[alt*='Википедия']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        driver.get(url);
    }

    public SearchResultsPage searchFor(String query) {
        WebElement input = waitForElementVisible(searchInput);
        input.clear();
        input.sendKeys(query);
        input.sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    public boolean isLogoDisplayed() {
        return isElementDisplayed(logo);
    }

    public boolean isSearchInputDisplayed() {
        return isElementDisplayed(searchInput);
    }
}
