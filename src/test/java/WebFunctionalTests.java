import Base.WebBaseTest;
import Helpers.DriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utilities.Session;
import Managers.LocalDriverManager;
import org.testng.annotations.*;

public class WebFunctionalTests extends WebBaseTest {

	@Test
	public void localDriverManagerTest(){
		String url = "https://www.seleniumeasy.com/test/basic-first-form-demo.html";
		WebDriver driver = LocalDriverManager.INSTANCE.getDriver();
		driver.get(url);
		assert driver.getCurrentUrl().equals(url);
	}

	@Test()
	public void driverHelperTest(){
		String url = "http://www.seleniumeasy.com/test/basic-first-form-demo.html";
		String userMessage = "This is a message", userMessageXpath = "//input[@id='user-message']";

		DriverHelper dh = new DriverHelper();
		dh.getURL(url);

		dh.setText(By.xpath(userMessageXpath), userMessage);

		assert dh.getValue(By.xpath(userMessageXpath)).equals(userMessage);
		dh.click(By.xpath("//button[text()='Show Message']"));
		assert dh.getText("//div[@id='user-message']").equals("Your Message: " + userMessage);
	}

	@Test
	public void sessionTest(){
		assert Session.getGrid().equals("gridURL String") : "\nExpected:\tgridURL String\nFound:\t\t" + Session.Companion.getGrid();
		assert Session.getEnv().equals("environment String");
		assert Session.isLocal();
		assert Session.isDesktop();
	}
}
