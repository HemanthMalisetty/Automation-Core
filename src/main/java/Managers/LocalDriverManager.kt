package Managers

import org.openqa.selenium.WebDriver

/**
 * This is where we set and get [org.openqa.selenium.WebDriver](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/WebDriver.html) to a Thread
 * so that tests can be easily run in parallel
 *
 */
object LocalDriverManager : LocalDriverBase<WebDriver>()