package Base;

import Managers.LocalDriverManager;
import Utilities.Session;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

public class WebBaseTest extends BaseTest {

    @BeforeTest
    @Parameters({"isMobile","deviceName","isLocal"})
    public void beforeTest(@Optional("false") Boolean isMobile, @Optional("") String deviceName, @Optional("true") Boolean isLocal) {

        if (isLocal) {
            new Session("environment String", true, true);
            Session.setGrid("gridURL String");
        }
        //endregion
        WebDriverManager.firefoxdriver().version("0.19.0").setup();
        WebDriverManager.chromedriver().setup();
        WebDriverManager.edgedriver().setup();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void beforeMethod(@Optional("chrome") String browser){
        if(browser.toLowerCase().equals("chrome")) {

            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "Pixel 2");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

            WebDriver driver = new ChromeDriver(chromeOptions);
            LocalDriverManager.INSTANCE.setDriver(driver);
        }
        if(browser.toLowerCase().equals("firefox")) {
            WebDriver driver = new FirefoxDriver();
            LocalDriverManager.INSTANCE.setDriver(driver);
        }
    }

    @AfterMethod
    public void afterMethod() {
        LocalDriverManager.INSTANCE.getDriver().quit();
    }
}
