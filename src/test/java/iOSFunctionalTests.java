import Base.MobileBaseTest;
import Helpers.MobileDriverHelper;
import io.appium.java_client.MobileBy;
import org.testng.Assert;
import org.testng.annotations.Test;

public class iOSFunctionalTests extends MobileBaseTest {

    @Test()
    public void settingsTest(){
        MobileDriverHelper.clickElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeCell' AND name = 'Sign in to your iPhone'"));
        MobileDriverHelper.clickElement(MobileBy.iOSNsPredicateString("type='XCUIElementTypeLink' AND name='About Apple ID and Privacy'"));

        Assert.assertTrue(MobileDriverHelper.isDisplayed(MobileBy.AccessibilityId("What is an Apple ID?")), "What is an Apple ID? is not 'displayed'");
    }
}
