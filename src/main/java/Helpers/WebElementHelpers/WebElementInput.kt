package Helpers.WebElementHelpers

import org.openqa.selenium.By

open class WebElementInput<T>(locator:By) : WebElementBase<T>(locator) {
    fun setText(text:String) : T{
        dh.setText(locator, text)
        return this as T
    }

    fun getValue(): String{
        return dh.getValue(locator)
    }
}