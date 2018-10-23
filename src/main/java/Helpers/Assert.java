package Helpers;

public class Assert {

    private Assert(){

    }

    static public void assertTrue(Boolean condition, String failMessage)
    {
        org.testng.Assert.assertTrue(condition, failMessage);
    }

    static public void assertFalse(Boolean condition, String failMessage)
    {
        org.testng.Assert.assertFalse(condition, failMessage);
    }

    static public void assertEquals(String actual, String expected, String failMessage)
    {
        org.testng.Assert.assertEquals(actual, expected, failMessage);
    }
}