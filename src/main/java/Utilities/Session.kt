package Utilities

import java.time.LocalDateTime

/**
 * Session holds data relevant to the current run so that WebDrivers and Tests can make data based decisions
 * @param env: current environment
 * @param local: boolean for is the current run being run locally or via a remote webdriver
 * @param isDesktop: boolean for is the current run a desktop or mobile run
 */
class Session(env: String, local: Boolean?, isDesktop: Boolean?) {

    /**
     * Constructor for Session, assigns passed in params to variables held by this object
     */
    init {
        Companion.env = env
        isLocal = local
        startTime = LocalDateTime.now()
        Companion.isDesktop = isDesktop
    }

    companion object {
        @JvmStatic
        var env: String? = null
            private set(env) {
                field = env
            }

        /**
         * Stores the grid url used to create remote webdrivers
         */
        @JvmStatic
        var grid: String? = null

        /**
         * Stores the Browser that the tests are being run on (Chrome, Firefox, Explorer)
         */
        @JvmStatic
        var browser: String? = null

        /**
         * Stores the start time of the suite run in [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html) object
         */
        @JvmStatic
        var startTime: LocalDateTime? = null

        /**
         * Stores whether the test is being run locally or not
         */
        @JvmStatic
        var isLocal: Boolean? = null
            private set

        /**
         * Stores whether the test is being run on Desktop or not
         */
        @JvmStatic
        var isDesktop: Boolean? = null
            private set

        //TODO move this
        /**
         * Stores excel Header used to retrieve data from excel files
         */
        @JvmStatic
        var excelHeader: String? = null

        // TODO Move to utilities class
        /**
         * Using start time variable this returns a more readable start time of the test suite
         */
        @JvmStatic
        val prettyStartTime: String
            get() =
                startTime!!.monthValue.toString() + "_" + startTime!!.dayOfMonth + "_" + startTime!!.hour + "_" + startTime!!.minute
        /**
         * returns current [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/[LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html).html) in a readable format
         */
        @JvmStatic
        val prettyTime: String
            get() {
                val ldt = LocalDateTime.now()
                return (ldt.monthValue.toString() + "_" + ldt.dayOfMonth + "_" + ldt.hour + "_" + ldt.minute
                        + "_" + ldt.second + "_" + ldt.nano)
            }

        /**
         * Stores whether test is being run on mobile, uses isDesktop as helper method
         */
        @JvmStatic
        val isMobile: Boolean?
            get() = !isDesktop!!
    }
}
