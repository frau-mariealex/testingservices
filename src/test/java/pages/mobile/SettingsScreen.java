package pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SettingsScreen extends BasePage {

    private final By settingsOption = By.xpath("//android.widget.TextView[@text='Settings']");
    private final By settingsTitle = By.xpath("//android.widget.TextView[@text='Settings']");
    private final By languagePreference = By.xpath("//*[contains(@text, 'Wikipedia language')]");

    public SettingsScreen(AndroidDriver driver) {
        super(driver);
    }

    public void openSettings() {
        click(settingsOption);
    }

    public boolean isSettingsScreenDisplayed() {
        return isElementDisplayed(settingsTitle) || isElementDisplayed(languagePreference);
    }
}
