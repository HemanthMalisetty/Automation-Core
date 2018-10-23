package Helpers;

import Managers.LocalDriverManagerMobile;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Methods used for the driver to execute common tasks
 */
public class MobileDriverHelper
{
	private MobileDriverHelper(){}

	/**
	 * Clicks on an element that is displayed on the device at the time.
	 * @param by
	 */
	public static void clickElement(By by)
	{
		getElement(by).click();
	}

	/**
	 * Finds and returns an element.
	 * @param by
	 * @return
	 */
	public static MobileElement getElement(By by)
	{
		return LocalDriverManagerMobile.INSTANCE.getDriver().findElement(by);
	}

	/**
	 * Returns a list of Web Elements
	 * @param by
	 * @return
	 */
	public static List<MobileElement> getElements(By by)
	{
		return LocalDriverManagerMobile.INSTANCE.getDriver().findElements(by);
	}

	/**
	 * Returns a boolean to see if the driver is an Android driver.
	 * @return
	 */
	public static boolean isAndroidDriver()
	{
		return LocalDriverManagerMobile.INSTANCE.getDriver() instanceof AndroidDriver;
	}

	/**
	 * Returns a boolean to see if the driver is an iOS driver.
	 * @return
	 */

	public static boolean isIOSDriver()
	{
		return LocalDriverManagerMobile.INSTANCE.getDriver() instanceof IOSDriver;
	}

	/**
	 * Returns a boolean value based on if the element is currently displayed.
	 * @param by
	 * @return
	 */
	public static boolean isDisplayed(By by)
	{
		List<MobileElement> elements = LocalDriverManagerMobile.INSTANCE.getDriver().findElements(by);
		try
		{
			return elements.size() > 0 && elements.get(0).isDisplayed();
		}
		catch(WebDriverException e)
		{
			System.out.println(Arrays.toString(e.getStackTrace()));
			return false;
		}
	}

	/**
	 * Waits for an element to be displayed and returns the mobile element.
	 * @param by
	 * @param timeoutInSeconds
	 * @return
	 */
	public static MobileElement waitForElementToAppearToUse(By by, int timeoutInSeconds)
	{
		return new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(), timeoutInSeconds).until(new ExpectedCondition<MobileElement>()
		{
			@Override
			public MobileElement apply(WebDriver input)
			{
				List<MobileElement> elements = LocalDriverManagerMobile.INSTANCE.getDriver().findElements(by);
				try
				{
					if(elements.size() > 0 && elements.get(0).isDisplayed())
					return elements.get(0);
				}
				catch(WebDriverException e)
				{
					System.out.println(Arrays.toString(e.getStackTrace()));
				}
				return null;
			}

			@Override
			public String toString()
			{
				return "element to appear, located by: '" + by.toString() + "'.\n";
			}
		});
	}

	/**
	 * Waits for an element to appear.
	 * @param by
	 * @param timeoutInSeconds
	 */
	public static void waitToAppear(By by, int timeoutInSeconds)
	{
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(), timeoutInSeconds).until(new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply(WebDriver input)
			{
				return isDisplayed(by);
			}

			@Override
			public String toString()
			{
				return "element to appear, located by: '" + by.toString() + "'.\n";
			}
		});
	}

	/**
	 * Overloaded method that accepts a mobile element.
	 * @param element
	 * @param timeoutInSeconds
	 */
	public static void waitToAppear(MobileElement element, int timeoutInSeconds)
	{
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(), timeoutInSeconds).until(new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply(WebDriver input)
			{
				return element.isDisplayed();
			}

			@Override
			public String toString()
			{
				return "element: '" + element + "' to appear.\n";
			}
		});
	}

	/**
	 * Wait for an element to become present.
	 * @param by
	 * @param timeoutInSeconds
	 */
	public static void waitToBePresent(By by, int timeoutInSeconds)
	{
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(), timeoutInSeconds).until(new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply(WebDriver input)
			{
				return LocalDriverManagerMobile.INSTANCE.getDriver().findElements(by).size() > 0;
			}

			@Override
			public String toString()
			{
				return "element to be present, located by: '" + by.toString() + "'.\n";
			}
		});
	}

	/**
	 * Wait for an element to disappear.
	 * @param by
	 * @param timeoutInSeconds
	 */
	public static void waitToDisappear(By by, int timeoutInSeconds)
	{
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(), timeoutInSeconds).until(new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply(WebDriver input)
			{
				return !isDisplayed(by);
			}

			@Override
			public String toString()
			{
				return "element to disappear, located by: '" + by.toString() + "'.\n";
			}
		});
	}

	/**
	 * Check to see if an element is present.
	 * @param by
	 * @return
	 */
	public static boolean isPresent(By by)
	{
		return LocalDriverManagerMobile.INSTANCE.getDriver().findElements(by).size() > 0;
	}

	/**
	 * Clears the text block and sets the text.
	 * @param element
	 * @param text
	 */
	public static void setValue(MobileElement element, String text)
	{
		element.clear();
		element.setValue(text);
	}

	/**
	 * Clears the text block and sets the text.
	 * @param by
	 */
	public static String getText(By by)
	{
		return LocalDriverManagerMobile.INSTANCE.getDriver().findElement(by).getText();
	}

	/**
	 * Selects an option and removes the keyboard.
	 * @param element
	 * @param option
	 */
	public static void selectOptionAndRemoveKeyboard(WebElement element, String option)
	{
		if(isIOSDriver())
		{
			element.click();
			waitForElementToAppearToUse(By.xpath("//XCUIElementTypePickerWheel"), 5).sendKeys(option);
			clickElement(MobileBy.AccessibilityId("Done"));
			new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(), 5).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//XCUIElementTypePickerWheel")));
		}else if(isAndroidDriver())//TODO: added
		{
			//this is handled in AndroidSCrolling class -- > scrollAndSearchSpinnerVertically method
		}
	}

	/**
	 * selects and option and clicks done on the keyboard to make it disappear.
	 * @param element
	 * @param option
	 */
	public static void selectOptionAndClickDoneKeyboard(WebElement element, String option)
	{
		if(isIOSDriver())
		{
			element.click();
			waitForElementToAppearToUse(By.xpath("//XCUIElementTypePickerWheel"), 5).sendKeys(option);
			getElement(By.xpath("//XCUIElementTypeToolbar/XCUIElementTypeButton[@label=\"Done\"]")).click();
			wait(5).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//XCUIElementTypePickerWheel")));
		}else if(isAndroidDriver())//TODO: added
		{
			//this is handled in AndroidSCrolling class -- > scrollAndSearchSpinnerVertically method
		}
	}

	/**
	 * Waits for a specified amount of time.
	 * @param timeout
	 * @return
	 */
	public static WebDriverWait wait(int timeout)
	{
		return new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(), timeout);
	}


	//region Description: Spinner actions for android only

	/**
	 * Waits for the spinner to become clickable,clicks it, then waits for the date option to become visible and clicks it.
	 * @param spinner
	 * @param option
	 */
	public static void selectDate(By spinner, By option) {
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(),15)
				.until(ExpectedConditions.elementToBeClickable(spinner)).click();
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(),15)
				.until(ExpectedConditions.visibilityOfElementLocated(option)).click();
	}

	/**
	 * Waits for the spinner to become clickable,clicks it, then waits for the year option to become visible and clicks it.
	 * @param spinner
	 * @param option
	 */
	public static void selectYear(By spinner, By option) {
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(),15)
				.until(ExpectedConditions.elementToBeClickable(spinner)).click();
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(),15)
				.until(ExpectedConditions.visibilityOfElementLocated(option)).click();
	}
	//endregion


	//region Description: Clicking Party for android only

	/**
	 * Clicks on an element.
	 * @param object The type of element it is: Button, RadioButton, Checkbox, Link, Dropdown
	 * @param typeOfElement
	 * @param timeInSeconds
	 */
	public static void clickElements(Object object, String typeOfElement, int timeInSeconds){
		System.out.println("----->inside clickElements");//TODO delete
		//set element
		MobileElement element;
		if(object instanceof  By){
			System.out.println("----->inside clickButtonElement - instance of By found");//TODO delete
			element = (MobileElement) new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(),timeInSeconds)
					.until(ExpectedConditions.visibilityOfElementLocated((By)object));
		}else if(object instanceof  MobileElement){
			System.out.println("----->inside clickButtonElement - instance of MobileElement found");//TODO delete
			element = (MobileElement) new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(),timeInSeconds)
					.until(ExpectedConditions.visibilityOf((MobileElement)object));
		}else throw new RuntimeException("Object type not found, could be due to element not found!!!");

		if(typeOfElement.toLowerCase().equals("button")){
			clickButton(element);
			return;
		}
		if(typeOfElement.toLowerCase().equals("radiobutton")){
			clickRadioButton(element);
			return;
		}
		if(typeOfElement.toLowerCase().equals("checkbox")){
			clickCheckBox(element);
			return;
		}
		if(typeOfElement.toLowerCase().equals("link")){
			clickLink(element);
			return;
		}
		if(typeOfElement.toLowerCase().equals("dropdown")){
			clickDropDown(element);
			return;
		}else{
			throw new RuntimeException("element type is not recognized!!");
		}
	}

	private static void clickButton(MobileElement element){
		System.out.println("----->inside clickButton");//TODO delete
		element.click();
		waitForProgressBarToDisappear();
		checkForMSError();
	}

	private static void clickRadioButton(MobileElement element){
		System.out.println("----->inside clickRadioButton");//TODO delete
		if(element.getAttribute("checked").equals("false")){
			element.click();
			waitForProgressBarToDisappear();
			checkForMSError();
			Assert.assertTrue(element.getAttribute("checked").equals("true"));
		}else if(element.getAttribute("checked").equals("true"))
			System.err.println("Warning - radioButton is already selected");
	}

	private static void clickCheckBox(MobileElement element){
		System.out.println("----->inside clickCheckBox");//TODO delete
		if(element.getAttribute("checked").equals("false")){
			element.click();
			waitForProgressBarToDisappear();
			checkForMSError();
			Assert.assertTrue(element.getAttribute("checked").equals("true"));
		}else if(element.getAttribute("checked").equals("true"))
			System.err.println("Warning - checkbox is already checked");
	}

	private static void clickLink(MobileElement element){
		System.out.println("----->inside clickLink");//TODO delete
		element.click();
		waitForProgressBarToDisappear();
		checkForMSError();
	}

	private static void clickDropDown(MobileElement element){
		System.out.println("----->inside clickDropDown");//TODO delete
		element.click();
		waitForProgressBarToDisappear();
		checkForMSError();
	}
	//endregion


	//region Description: Tapping (instead if clicking) Party for android only



	//region Description: Used by both tapping and clicing party - waits for progress bars and MS errors

	/**
	 * Waits for progress bar to disappear (Android only solution).
	 */
	private static void waitForProgressBarToDisappear(){
		By androidProgressBar = By.xpath("//*[@class=\"android.widget.ProgressBar\"]");
		System.out.println("----->inside waitForProgressBarToDisappear");
		//progress may flicker causing failures
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(LocalDriverManagerMobile.INSTANCE.getDriver().findElements(androidProgressBar).size() > 0){
			System.out.println("----->inside waitForProgressBarToDisappear - progress Bar found and waiting for it to clear");//TODO delete
			try{
				new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(),20)
						.until(ExpectedConditions.invisibilityOfElementLocated(androidProgressBar));
			}catch (TimeoutException t){
				System.err.println("-----> Progress bar flickered or got stucked!!!");
			}
		}
	}

	/**
	 * Checks for any microservice error (Android only solution).
	 */
	private static void checkForMSError(){
		By customDialogContainer = By.id("com.universalstudios.orlandoresort:id/custom_dialog_container");
		System.out.println("----->inside checkForMSError");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if((LocalDriverManagerMobile.INSTANCE.getDriver().findElements(customDialogContainer).size() > 0)) {
			System.err.println("----->inside clickButtonElement - Oh boy oh boy - found custom container!!!");

			By dialogTextElement = By.xpath("//*[@resource-id=\"com.universalstudios.orlandoresort:id/custom_dialog_container\"]/android.widget.LinearLayout/android.widget.TextView");
			String dialogText = LocalDriverManagerMobile.INSTANCE.getDriver().findElement(dialogTextElement).getText();

			if(dialogText.contains("something went wrong") || dialogText.contains("we are unable to"))
				throw new AssertionError("MS Error !!! ---> " + dialogText);
		}else {
			//TODO I can implement a tap to dismiss the container
			System.err.println("-----> no custom container found...");
		}
	}

	/**
	 * 	Checking for MS errors - android. Specifically an Email error
	 */
	public static void checkEmailError(){
		By errorSelector = By.xpath("//*[@resource-id=\"com.universalstudios.orlandoresort:id/tvErrorEmail\"]");
		waitForProgressBarToDisappear();
		new WebDriverWait(LocalDriverManagerMobile.INSTANCE.getDriver(),5).until(new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver driver){
				if(driver.findElements(errorSelector).size() > 0){
					throw new RuntimeException("Email Message Error!!!");
				}else if(!(driver.findElements(errorSelector).size() > 0)) {
					return true;
				}
				return false;
			}

			@Override
			public String toString() { return "waiting to verify no email error has been genreated...";}
		});
	}
	//endregion



}