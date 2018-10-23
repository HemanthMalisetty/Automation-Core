import Helpers.Assert;
import org.testng.annotations.Test;

public class UnitTests {

    @Test()
    public void assertionBuilderTest(){
        Assert.assertTrue(true, "Assert True Fail Message");
        Assert.assertFalse(false, "Assert False Fail Message");
        Assert.assertEquals("Equal String", "Equal String", "Assert Equals Fail Message");
    }
}
