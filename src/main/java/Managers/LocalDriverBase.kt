package Managers


/**
 * This is where the driver is set and returned when needed.
 */
abstract class LocalDriverBase<T> {

    private val driver = ThreadLocal<T>()

    /**
     * This property gets or returns the current threads WebDriver
     */
    open fun getDriver(): T {
        return driver.get()
    }

    /**
     * This method takes in a driver and sets it to the current thread
     * @param driver: [WebDriver](https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/WebDriver.html) to be bound to the current thread
     */
    open fun setDriver(driver: T) {
        this.driver.set(driver)
    }

}
