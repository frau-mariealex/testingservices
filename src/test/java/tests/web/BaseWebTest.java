package tests.web;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;
import utils.DriverFactory;

public class BaseWebTest {
    protected WebDriver driver;
    protected String baseUrl;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.createWebDriver();
        baseUrl = ConfigReader.get("web.base.url");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void openHomePage() {
        driver.get(baseUrl);
    }
}
