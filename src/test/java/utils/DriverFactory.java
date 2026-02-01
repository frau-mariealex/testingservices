package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    public static WebDriver createWebDriver() {
        String browser = ConfigReader.get("web.browser").toLowerCase();
        WebDriver driver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getInt("web.implicit.wait"))
        );
        driver.manage().window().maximize();

        return driver;
    }

    public static AndroidDriver createMobileDriver() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.get("appium.platform.name"));
        options.setPlatformVersion(ConfigReader.get("appium.platform.version"));
        options.setDeviceName(ConfigReader.get("appium.device.name"));
        options.setAppPackage(ConfigReader.get("appium.app.package"));
        options.setAppActivity(ConfigReader.get("appium.app.activity"));
        options.setAutomationName(ConfigReader.get("appium.automation.name"));
        options.setNoReset(ConfigReader.getBoolean("appium.no.reset"));
        options.setNewCommandTimeout(Duration.ofSeconds(60));

        try {
            URL appiumServerUrl = new URL(ConfigReader.get("appium.server.url"));
            return new AndroidDriver(appiumServerUrl, options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + e.getMessage());
        }
    }
}
