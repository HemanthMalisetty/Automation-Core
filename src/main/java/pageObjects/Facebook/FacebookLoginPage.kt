package pageObjects.Facebook

import Helpers.WebElementHelpers.WebElementInput
import org.openqa.selenium.By

class FacebookLoginPage : PageObjectBase<FacebookLoginPage>(){
    var email = WebElementInput(By.xpath("//input[@id='email']"), this)
    var password = WebElementInput(By.xpath("//input[@id='password']"), this)
}