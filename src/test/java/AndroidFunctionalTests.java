import Base.MobileBaseTest;
import Helpers.MobileDriverHelper;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AndroidFunctionalTests extends MobileBaseTest {

    @Test()
    public void androidCoreTestApplication()
    {
        MobileDriverHelper.clickElement(By.id("com.universalorlando.androidcoretestapplication:id/coreButton"));
        String textView = MobileDriverHelper.getText(By.id("com.universalorlando.androidcoretestapplication:id/coreTextView"));
        Assert.assertEquals(textView, "Core Project!", "Text was: '" +textView + "'");
    }
}
