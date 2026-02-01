package tests.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.DriverFactory;

import java.time.Duration;

public class BaseMobileTest {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.createMobileDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
