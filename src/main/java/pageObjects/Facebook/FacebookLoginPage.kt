package pageObjects.Facebook

import Helpers.WebElementHelpers.WebElementInput
import org.openqa.selenium.By

open class FacebookLoginPage : PageObjectBase(){
    var email = WebElementInput<FacebookLoginPage>(By.xpath("//input[@id='email']"))
}