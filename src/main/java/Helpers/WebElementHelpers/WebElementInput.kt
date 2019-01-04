package Helpers.WebElementHelpers

import org.openqa.selenium.By
import pageObjects.Facebook.PageObjectBase

open class WebElementInput<T : PageObjectBase<T>>(locator:By, page : T) : WebElementBase<T>(locator, page) {

    fun setText(text:String) : T{
        dh.setText(locator, text)
        return page
    }

    fun getValue(): String{
        return dh.getValue(locator)
    }
}