package Helpers.WebElementHelpers

import Helpers.DriverHelper
import org.openqa.selenium.By
import pageObjects.Facebook.PageObjectBase

abstract class WebElementBase<T>(locator: By) : PageObjectBase(){
    protected var locator :By = locator
    protected var dh : DriverHelper = DriverHelper()
}