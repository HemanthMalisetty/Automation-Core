package Base;

import Utilities.Session;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;


abstract class BaseTest {

    protected static AppiumDriverLocalService appiumDriverLocalService;
    protected DesiredCapabilities capabilities;
    protected static Session session = null;
    protected boolean isLocal;
    protected String startExecutionTime;
    protected String deviceName;

}
