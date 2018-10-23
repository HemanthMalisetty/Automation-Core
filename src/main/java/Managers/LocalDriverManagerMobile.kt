package Managers

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement

/**
 * Mobile driver manager that handles all thread specific and non specific cases.
 */
object LocalDriverManagerMobile : LocalDriverBase<AppiumDriver<MobileElement>>()