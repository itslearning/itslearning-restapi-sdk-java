/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Amund
 */
public class CryptographyHelperTest
{

    public CryptographyHelperTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of computeHash method, of class CryptographyHelper.
     */
    @Test
    public void testComputeHash_ApiSession_String()
    {
        System.out.println("computeHash");
        String sharedSecret = "MySecret123";
        String appKey = "MyAppKey123";

        ApiSession session = new ApiSession();
        session.setApplicationKey(appKey);
        session.setLastRequestDateTimeUtc(Calendar.getInstance().getTime());
        session.setSessionId("MySessionId");
        String expResult = "";
        String result = CryptographyHelper.computeHash(session, sharedSecret);
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
    }
}