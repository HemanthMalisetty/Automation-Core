package Helpers

import Managers.LocalDriverManager
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait


import java.util.stream.Collectors


/**
 * Helper Methods used for common web testing functionalities.
 */
open class DriverHelper {

    /**
     *  Standard Wait time standard used across methods
     */
    protected var defaultWaitTime = 20
    /**
     *  Select helper used for drop down menus
     */
    var dhs = SelectHelper()
        private set
    /**
     * References the driver which is used throughout the lifetime of the instance of this class
     */
    var driver = LocalDriverManager.getDriver()
        protected set

    /**
     * Used to click on objects on the page, will continue to click for 5 seconds, if unclickable, it will throw
     * and error.
     * @param locator: [By](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/By.html) Locator used to find the element on the page
     * @param message: message that is reported if the click failed
     */
    @JvmOverloads
    open fun click(locator: By, message: String  = "$locator  cannot be clicked") {
        waitForElementToBeClickable(locator, message)

        WebDriverWait(driver, 5).until(object : ExpectedCondition<Boolean> {
            override fun apply(webDriver: WebDriver?): Boolean? {
                try {
                    driver.findElement(locator).click()
                } catch (e: WebDriverException) {
                    println("Failed to click locator: " + locator)
                    return false
                }

                return true
            }

            override fun toString(): String {
                return message
            }

        })
    }

    /**
     * Overrided click method to take in an xpath as a String instead of a locator.
     * @param xpath: Xpath locator in a string used to locate the element
     * @param message: message to be produced if the click fails
     */
    @JvmOverloads
    open fun click(xpath: String, message: String = "$xpath  cannot be clicked") {
        click(By.xpath(xpath), message)
    }

    /**
     * Method to set text into an search box or pass file string into an input button. Waits 5 seconds for the element
     * to become visible before attempting to input the string.
     * @param locator: [By](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/By.html) Locator used to find the element on the page
     * @param text: [java.lang.String] is Text to be set in the input found by the locator
     */
    fun setText(locator: By, text: String) {
        waitForElementToExist(locator)
        WebDriverWait(LocalDriverManager.getDriver(), 5).until(ExpectedConditions.visibilityOfElementLocated(locator))
        driver.findElement(locator).clear()
        driver.findElement(locator).sendKeys(text)
    }

    /**
     * Overrided method to accept a xpath instead of a locator.
     */
    fun setText(xpath: String, text: String) {
        setText(By.xpath(xpath), text)
    }

    /**
     * Method to get text from any non input element. Waits for the element to exists then retrieves the text.
     */
    open fun getText(locator: By): String {
        waitForElementToExist(locator)
        return driver.findElement(locator).text
    }

    /**
     * Overrided method to accept xpath instead of a locator.
     */
    fun getText(xpath: String): String {
        return this.getText(By.xpath(xpath))
    }

    /**
     * Method to get text from an input box. Does not have any wait conditions.
     */
    fun getValue(by: By): String {
        return getAttribute(by, "value")
    }

    /**
     * Returns any value of the attribute you request associated to the element
     */
    fun getAttribute(by: By, attribute: String): String {
        return driver.findElement(by).getAttribute(attribute)
    }

    /**
     * Returns the current Url of the webpage present
     */
    fun getURL(url: String) {
        driver.get(url)
    }

    /**
     * Finds elements from the xpath passed in and returns a List of webelements.
     */
    fun findElements(xpath: String): List<WebElement> {
        return findElements(By.xpath(xpath))
    }

    /**
     * Overrided method that accepts a locator instead of a xpath
     */
    fun findElements(locator: By): List<WebElement> {
        return driver.findElements(locator)
    }

    /**
     * Finds and returns the element from the locator passed in.
     */
    fun findElement(locator: By): WebElement {
        return driver.findElement(locator)
    }

    /**
     * Overrided method that takes in a xpath instead of locator.
     */
    fun findElement(xpath: String): WebElement {
        return findElement(By.xpath(xpath))
    }

    /**
     * Returns boolean depending on whether the element is selected from the drop drown.
     */
    fun isSelected(locator: By): Boolean? {
        waitForElementToExist(locator)
        return driver.findElement(locator).isSelected
    }

    /**
     * Overrided method that takes in a xpath instead of a locator.
     */
    fun isSelected(xpath: String): Boolean? {
        return isSelected(By.xpath(xpath))
    }
    /**
     * Returns boolean to see if the element is displayed on webpage.
     */
    fun isDisplayed(by: By): Boolean {
        val elements = driver.findElements(by)
        try {
            return !elements.isEmpty() && elements[0].isDisplayed
        } catch (e: StaleElementReferenceException) {
            return false
        }

    }

    /**
     * Method to see if an element is clickable or faded out.
     */
    fun isClickable(locator: By): Boolean? {
        waitForElementToExist(locator)
        return driver.findElement(locator).isEnabled
    }

    /**
     * Method to simulate the press of the tab key.
     */
    fun tab() {
        driver.findElement(By.tagName("body")).sendKeys(Keys.TAB)
    }

    /**
     * Overrided method that sets doesn't need the waitTime specified.
     */
    fun waitForElementToBeClickable(locator: By, message: String) {
        waitForElementToBeClickable(locator, defaultWaitTime, message)
    }

    /**
     * Overrided method that doesnt need the waitTime or message specified.
     */
    fun waitForElementToBeClickable(xpath: String) {
        waitForElementToBeClickable(By.xpath(xpath))
    }

    /**
     * Waits for the default time(20 secs) for an element to become clickable.
     */
    @JvmOverloads
    fun waitForElementToBeClickable(locator: By, waitTime: Int = defaultWaitTime) {
        WebDriverWait(driver, waitTime.toLong()).until(object : ExpectedCondition<WebElement> {
            override fun apply(webDriver: WebDriver?): WebElement? {
                return ExpectedConditions.elementToBeClickable(locator).apply(driver)
            }

            override fun toString(): String {
                return waitTime.toString() + " seconds for element to be clickable at locator: '" + locator + "'\n"
            }
        })
    }
    /**
     * Waits for a specified amount of time for an element to become clickable and throws a specified error if not
     * clickable.
     */
    fun waitForElementToBeClickable(locator: By, waitTime: Int, message: String) {
        WebDriverWait(driver, waitTime.toLong()).until(object : ExpectedCondition<WebElement> {
            override fun apply(webDriver: WebDriver?): WebElement? {
                return ExpectedConditions.elementToBeClickable(locator).apply(driver)
            }

            override fun toString(): String {
                return waitTime.toString() + " seconds for: " + message
            }
        })
    }

    /**
     * Waits for an element to be visible. Defaulted to 20 secs but can be set to any arbitrary time.
     */
    @JvmOverloads
    fun waitForElementToBeVisible(locator: By, waitTime: Int = defaultWaitTime) {
        WebDriverWait(driver, waitTime.toLong()).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator))
    }

    /**
     * Overrided method that doesnt need the wait time specified and also accepts a xpath string instead of a locator.
     */
    fun waitForElementToBeVisible(xpath: String) {
        waitForElementToBeVisible(By.xpath(xpath))
    }

    /**
     * Waits for the element to exist on the webpage for a default time of 20 secs
     * which can be specified otherwise as well.
     */
    @JvmOverloads
    fun waitForElementToExist(locator: By, waitTime: Int = defaultWaitTime) {
        WebDriverWait(driver, waitTime.toLong()).until(object : ExpectedCondition<Boolean> {
            override fun apply(webDriver: WebDriver?): Boolean? {
                return elementExists(locator)
            }

            override fun toString(): String {
                return "element to exist. Locator: '$locator'"
            }
        })
    }

    /**
     * Overrided method that takes in an xpath instead of a locator.
     */
    fun waitForElementToExist(xpath: String) {
        waitForElementToExist(By.xpath(xpath))
    }

    /**
     * Overrided method that takes takes in an xpath and an explicity stated waitTime (soon to be deprecated)
     */
    fun waitForElementToExist(xpath: String, waitTime: Int) {
        waitForElementToExist(By.xpath(xpath), waitTime)
    }
    /**
     * Returns a boolean depending if an element exists on the webpage.
     */
    fun elementExists(locator: By): Boolean{
        return driver.findElements(locator).size > 0
    }

    /**
     * Waits for a certain text to have the same value as the specified string passed in.
     */
    fun waitForTextToBe(locator: By, expectedText: String) {
        WebDriverWait(driver, 15).until(object : ExpectedCondition<Boolean> {
            override fun apply(webDriver: WebDriver?): Boolean? {
                return ExpectedConditions.textToBe(locator, expectedText).apply(driver)
            }

            override fun toString(): String {
                return "text at locator: '$locator' to be '$expectedText'."
            }
        })
    }

    /**
     * Waits for a certain text to have a different value as the specified string passed in.
     */
    fun waitForTextNotToBe(locator: By, unexpectedText: String) {
        WebDriverWait(driver, defaultWaitTime.toLong()).until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, unexpectedText)))
    }

    /**
     * Overrided method that takes in an xpath string instead of a locator.
     */
    fun waitForTextNotToBe(xpath: String, unexpectedText: String) {
        waitForTextNotToBe(By.xpath(xpath), unexpectedText)
    }

    /**
     * Waits for the url to be the same as the string passed in.DefaultTime is 20 secs.
     */
    fun waitForUrlToBe(url: String) {
        WebDriverWait(driver, defaultWaitTime.toLong()).until(ExpectedConditions.urlToBe(url))
    }

    /**
     * Waits for the url to contain the string passed in. DefaultTime is 20 secs.
     */
    @JvmOverloads
    fun waitForUrlToContain(url: String, timeInSeconds: Int = defaultWaitTime) {
        WebDriverWait(driver, timeInSeconds.toLong()).until(object : ExpectedCondition<Boolean> {
            override fun apply(webDriver: WebDriver?): Boolean? {
                return driver.currentUrl.contains(url)
            }

            override fun toString(): String {
                return "waiting for url to contain'" + url + "' but found '" + driver.currentUrl + "'"
            }
        })
    }

    //TODO Find better way to denote failure (receives a TimeoutException)

    //endregion

    /**
     * Scrolls to the top of the present webpage.
     */
    fun scrollToTopOfPage()
    {
        (driver as JavascriptExecutor).executeScript("window.scrollTo(0,0)")
    }

    /**
     * Class built to contain methods that assist with drop down menus.
     */
    inner class SelectHelper {

        /**
         * Selects an option from the specified drop down. Multiple options for locating the element.
         */
        fun selectOption(locator: By? = null, xpath: String? = null, option : String? = null, index : Int? = null)
        {
            var loc : By? = null

            when (xpath != null) {
                true -> loc = By.xpath(xpath)
                false -> loc = locator!! // Convert to non nullable type

                else -> println("Locator and xpath are null")

            }
            when (option != null)
            {
                true -> {
                    WebDriverWait(driver, defaultWaitTime.toLong()).until(object : ExpectedCondition<Boolean> {
                        override fun apply(webDriver: WebDriver?): Boolean? {
                            try {
                                Select(driver.findElement(loc)).selectByVisibleText(option)
                            } catch (e: WebDriverException) {
                                return false
                            }
                            return true
                        }

                        override fun toString(): String {
                            var options = Select(driver.findElement(loc)).options.stream().map(WebElement::getText).collect(Collectors.toList())
                            return "option to appear: '$option'\nFound the following options: {$options}"
                        }
                    })
                }
                false -> {
                    val ind = index!! //Convert to non nullable type
                    val select = Select(driver.findElement(locator))
                    WebDriverWait(driver, defaultWaitTime.toLong()).until(object : ExpectedCondition<Boolean> {
                        override fun apply(webDriver: WebDriver?): Boolean? {
                            try {
                                select.selectByIndex(ind)
                            } catch (e: NoSuchElementException) {
                                return false
                            }

                            return true
                        }

                        override fun toString(): String {
                            return "Could Not Find the option by index\nIndex Given: $index \nNumber of elements found:  ${select.options.size}"
                        }
                    })
                }
            }
        }

        /**
         * Overrided method that takes in a locator instead of the above mentioned locator types.
         */
        fun selectOption(locator: By, option: String) {
            WebDriverWait(driver, defaultWaitTime.toLong()).until(object : ExpectedCondition<Boolean> {
                override fun apply(webDriver: WebDriver?): Boolean? {
                    try {
                        Select(driver.findElement(locator)).selectByVisibleText(option)
                    } catch (e: WebDriverException) {
                        return false
                    }

                    return true
                }

                override fun toString(): String {
                    //val options = Select(driver.findElement(locator)).options.stream().map<String>(Function<WebElement, String> { it.getText() }).collect<ArrayList<String>, Any>(Collectors.toCollection(Supplier<ArrayList<String>> { ArrayList() }))
                    var options = Select(driver.findElement(locator)).options.stream().map(WebElement::getText).collect(Collectors.toList())
                    return "option to appear: '$option'\nFound the following options: {$options}"
                }
            })
        }

        /**
         * Overrided method that takes in an xpath and string for the option to be selected.
         */
        fun selectOption(xpath: String, option: String) {
            selectOption(By.xpath(xpath), option)
        }

        fun selectOption(locator: By, index: Int) {
            val select = Select(driver.findElement(locator))
            WebDriverWait(driver, defaultWaitTime.toLong()).until(object : ExpectedCondition<Boolean> {
                override fun apply(webDriver: WebDriver?): Boolean? {
                    try {
                        select.selectByIndex(index)
                    } catch (e: NoSuchElementException) {
                        return false
                    }

                    return true
                }

                override fun toString(): String {
                    return "Could Not Find the option by index\nIndex Given: $index \nNumber of elements found:  ${select.options.size}"
                }
            })
        }

        /**
         * Overrided method that takes in an xpath string and and index.
         */
        fun selectOption(xpath: String, index: Int) {
            selectOption(By.xpath(xpath), index)
        }

        /**
         * Returns a list of strings that contain the available options from the element specified.
         */
        fun getOptions(locator: By): List<String> {
            val select = Select(driver.findElement(locator))
            //return select.options.stream().map<String>(Function<WebElement, String> { it.getText() }).collect<ArrayList<String>, Any>(Collectors.toCollection(Supplier<ArrayList<String>> { ArrayList() }))
            return select.options.stream().map(WebElement::getText).collect(Collectors.toList())
        }

        /**
         * Returns the text of the option selected.
         */
        fun getSelectedOption(locator: By): String {
            return Select(findElement(locator)).firstSelectedOption.text
        }

        /**
         * Overrided method that takes in an xpath string instead of a locator.
         */
        fun getSelectedOption(xpath: String): String {
            return getSelectedOption(By.xpath(xpath))
        }
    }
}
