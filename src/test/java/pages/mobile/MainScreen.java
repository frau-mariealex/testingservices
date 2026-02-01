package pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class MainScreen extends BasePage {

    private final By searchContainer = By.id("org.wikipedia:id/search_container");
    private final By skipButton = By.id("org.wikipedia:id/fragment_onboarding_skip_button");
    private final By feedView = By.id("org.wikipedia:id/feed_view");
    private final By moreTab = By.id("org.wikipedia:id/nav_more_container");

    public MainScreen(AndroidDriver driver) {
        super(driver);
    }

    public void skipOnboarding() {
        if (isElementPresent(skipButton)) {
            click(skipButton);
        }
    }

    public boolean isMainScreenDisplayed() {
        skipOnboarding();
        return isElementDisplayed(searchContainer) || isElementDisplayed(feedView);
    }

    public SearchScreen clickSearchContainer() {
        click(searchContainer);
        return new SearchScreen(driver);
    }

    public boolean isSearchContainerDisplayed() {
        return isElementDisplayed(searchContainer);
    }

    public SettingsScreen clickMoreTab() {
        click(moreTab);
        return new SettingsScreen(driver);
    }
}
