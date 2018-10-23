package Base;

import Managers.LocalDriverManagerMobile;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class MobileBaseTest extends BaseTest {

    @BeforeSuite
    @Parameters({"deviceName","isLocal","avd","app","bundleID","appPackage","appActivity"})
    public void beforeSuite(String deviceName, @Optional("true") Boolean isLocal,@Optional("") String avd,@Optional("") String app,@Optional("")String bundleID,@Optional("")String appPackage,@Optional("")String appActivity) throws IOException {
        this.isLocal = isLocal;
        this.deviceName = deviceName;

        capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);

        if(!app.isEmpty())
            capabilities.setCapability(MobileCapabilityType.APP, app);
        if(!avd.isEmpty())
            capabilities.setCapability(AndroidMobileCapabilityType.AVD, avd);

        //region Local
        else {

            AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
            appiumServiceBuilder
                    .usingAnyFreePort()
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                    .withArgument(GeneralServerFlag.STRICT_CAPS)
                    .withArgument(GeneralServerFlag.LOG_TIMESTAMP);
            appiumDriverLocalService = AppiumDriverLocalService.buildService(appiumServiceBuilder);
            appiumDriverLocalService.start();

            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);


            // Iphone
            if (deviceName.toLowerCase().contains("iphone")) {

                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                capabilities.setCapability("waitForQuiescence", false);
                if (!bundleID.isEmpty())
                //TODO Remove this, add parameter and move into unitTest profile
                    capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, bundleID);
            }
            // Android
            else
            {
//                if(app.isEmpty())
//                {
//                    capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.universalstudios.orlandoresort");
//                    capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.universalstudios.orlandoresort.controller.userinterface.launcher.LauncherActivity");
//                }
//			capabilities.setCapability(MobileCapabilityType.NO_RESET,true);
//			capabilities.setCapability(MobileCapabilityType.FULL_RESET,false);
//			capabilities.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET,true);
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
                if (!appPackage.isEmpty())
                    capabilities.setCapability("appPackage", appPackage);
                if (!appActivity.isEmpty())
                    capabilities.setCapability("appActivity", appActivity);
            }
        }
        //endregion
    }
    @BeforeMethod
    public void beforeMethod(Method method){
        //region Local
        if (isLocal)
        {
            //region iPhone
            if (deviceName.toLowerCase().contains("iphone"))
                LocalDriverManagerMobile.INSTANCE.setDriver(new IOSDriver<>(appiumDriverLocalService.getUrl(), capabilities));
            //endregion

            //region Android
            else
                LocalDriverManagerMobile.INSTANCE.setDriver(new AndroidDriver<>(appiumDriverLocalService.getUrl(), capabilities));
            //endregion
        }
        //endregion
    }

    @AfterMethod
    public void afterMethod(ITestResult testResult, Object[] parameters) {
        if(!isLocal)
        {
            //turn off network virtualization
            LocalDriverManagerMobile.INSTANCE.getDriver().executeScript("mobile:vnetwork:stop", new HashMap<>());

            //TODO: logs shutdown - to avoid errors, i can add try catch statement to handle exceptions
            if(!deviceName.contains("iphone"))
                LocalDriverManagerMobile.INSTANCE.getDriver().executeScript("mobile:logs:stop");
        }

        LocalDriverManagerMobile.INSTANCE.getDriver().quit();
    }
}