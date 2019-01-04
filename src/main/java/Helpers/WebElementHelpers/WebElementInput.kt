package Helpers.WebElementHelpers

import org.openqa.selenium.By

open class WebElementInput(locator:By) : WebElementBase(locator) {
    fun setText(text:String){
        dh.setText(locator, text)
    }

    fun getValue(): String{
        return dh.getValue(locator)
    }
}