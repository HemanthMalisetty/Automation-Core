package Base;

import Managers.LocalDriverManager;
import Utilities.Session;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

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
    public void beforeMethod(@Optional("firefox") String browser){
        if(browser.toLowerCase().equals("chrome")) {
            WebDriver driver = new ChromeDriver();
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
