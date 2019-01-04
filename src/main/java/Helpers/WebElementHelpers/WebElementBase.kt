package Helpers.WebElementHelpers

import Helpers.DriverHelper
import org.openqa.selenium.By

abstract class WebElementBase(locator: By) {
    protected var locator :By = locator
    protected var dh : DriverHelper = DriverHelper()
}