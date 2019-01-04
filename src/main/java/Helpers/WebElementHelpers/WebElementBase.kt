package Helpers.WebElementHelpers

import Helpers.DriverHelper
import org.openqa.selenium.By
import pageObjects.Facebook.PageObjectBase

abstract class WebElementBase<T : PageObjectBase<T>>(locator: By, page : T){
    protected var locator :By = locator
    protected var dh : DriverHelper = DriverHelper()
    protected var page = page
}