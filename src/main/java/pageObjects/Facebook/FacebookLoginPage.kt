package pageObjects.Facebook

import Helpers.WebElementHelpers.WebElementInput
import org.openqa.selenium.By

open class FacebookLoginPage{
    var email = WebElementInput(By.xpath("//input[@id='email']"))
}