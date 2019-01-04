package WebElement

import Base.WebBaseTest
import Helpers.DriverHelper
import org.testng.annotations.Test
import pageObjects.Facebook.FacebookLoginPage

open class FacebookTest : WebBaseTest(){
    @Test
    fun loginToFacebookTest(){
        DriverHelper().getURL("https://www.facebook.com")

        FacebookLoginPage().email.setText("test")
        val email = FacebookLoginPage().email.getValue()
        print("wait here")
    }
}