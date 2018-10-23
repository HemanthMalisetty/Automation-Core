package Helpers;

import Managers.LocalDriverManagerMobile;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public class XCUIScroll
{
	/**
	 * Scrolls to element using <a href="https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/By.html">By</a> , passes XCUIElementTypeTable as a default scrollable container
	 * @param locator <a href="https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/By.html">By</a>
	 */
	public static void to(By locator)
	{
		to(MobileDriverHelper.getElement(By.className("XCUIElementTypeTable")), locator);
	}

	/**
	 * Scrolls to Element using <a href="https://appium.github.io/java-client/io/appium/java_client/MobileElement.html">MobileElement</a>, passes XCUIElementTypeTable as a default scrollable container
	 * @param element <a href="https://appium.github.io/java-client/io/appium/java_client/MobileElement.html">MobileElement</a>
	 */
	public static void to(MobileElement element)
	{
		to(MobileDriverHelper.getElement(By.className("XCUIElementTypeTable")), element);
	}

	/**
	 * Scrolls to the element of the <a href="https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/By.html">By</a> locator within the given scrollable <a href="https://appium.github.io/java-client/io/appium/java_client/MobileElement.html">MobileElement</a>
	 * @param element <a href="https://appium.github.io/java-client/io/appium/java_client/MobileElement.html">MobileElement</a> is the scrollable element
	 * @param locator <a href="https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/By.html">By</a> locates the element to be interacted with
	 */
	public static void to(MobileElement element, By locator)
	{
		Map<String, String> scrollParams = new HashMap<>();
		scrollParams.put("element", element.getId());

		String locatorString = locator.toString();
		locatorString = locatorString.substring(locatorString.indexOf(": ")+2);

		if(locator instanceof MobileBy.ByIosNsPredicate)
			scrollParams.put("predicateString", locatorString);
		else if(locator instanceof MobileBy.ByAccessibilityId)
			scrollParams.put("predicateString", "name == '" + locatorString + "'");
		else if(locator instanceof By.ByClassName)
			scrollParams.put("predicateString", "type == '" + locatorString + "'");

		LocalDriverManagerMobile.INSTANCE.getDriver().executeScript("mobile: scroll", scrollParams);
	}

	/**
	 Scrolls to the element of the <a href="https://appium.github.io/java-client/io/appium/java_client/MobileElement.html">MobileElement</a> locator within the given scrollable <a href="https://appium.github.io/java-client/io/appium/java_client/MobileElement.html">MobileElement</a>
	 * @param container <a href="https://appium.github.io/java-client/io/appium/java_client/MobileElement.html">MobileElement</a> is the scrollable element
	 * @param element <a href="https://appium.github.io/java-client/io/appium/java_client/MobileElement.html">MobileElement</a> locates the element to be interacted with
	 */
	public static void to(MobileElement container, MobileElement element)
	{
		Map<String, String> scrollParams = new HashMap<>();
		scrollParams.put("element", container.getId());

		String elementString = element.toString();

		if(elementString.startsWith("Located by Locator map:"))
		{
			String elementLocator = StringUtils.substringBetween(elementString, "{", "}");
			String[] elementLocatorAttributes = elementLocator.split(": ");

			switch(elementLocatorAttributes[0])
			{
				case "By.IosNsPredicate":
					scrollParams.put("predicateString", elementLocatorAttributes[1]);
					break;
				case "By.AccessibilityId":
					scrollParams.put("predicateString", "name == '" + elementLocatorAttributes[1] + "'");
					break;
				case "By.className":
					scrollParams.put("predicateString", "type == '" + elementLocatorAttributes[1] + "'");
					break;
			}
		}
		else
		{
			String elementLocator = elementString.substring(elementString.indexOf("->")+3, elementString.length()-1);
			String[] elementLocatorAttributes = elementLocator.split(": ");

			switch(elementLocatorAttributes[0])
			{
				case "-ios predicate string":
					scrollParams.put("predicateString", elementLocatorAttributes[1]);
					break;
				case "accessibility id":
					scrollParams.put("predicateString", "name == '" + elementLocatorAttributes[1] + "'");
					break;
				case "class name":
					scrollParams.put("predicateString", "type == '" + elementLocatorAttributes[1] + "'");
					break;
			}
		}

		LocalDriverManagerMobile.INSTANCE.getDriver().executeScript("mobile: scroll", scrollParams);
	}
}