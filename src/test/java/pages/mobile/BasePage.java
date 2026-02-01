package pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        waitForElementClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollDown() {
        int startX = driver.manage().window().getSize().width / 2;
        int startY = (int) (driver.manage().window().getSize().height * 0.8);
        int endY = (int) (driver.manage().window().getSize().height * 0.2);

        driver.executeScript("mobile: scrollGesture", java.util.Map.of(
            "left", startX - 50,
            "top", startY,
            "width", 100,
            "height", startY - endY,
            "direction", "down",
            "percent", 0.75
        ));
    }

    public void scrollUp() {
        int startX = driver.manage().window().getSize().width / 2;
        int startY = (int) (driver.manage().window().getSize().height * 0.2);
        int endY = (int) (driver.manage().window().getSize().height * 0.8);

        driver.executeScript("mobile: scrollGesture", java.util.Map.of(
            "left", startX - 50,
            "top", startY,
            "width", 100,
            "height", endY - startY,
            "direction", "up",
            "percent", 0.75
        ));
    }
}
