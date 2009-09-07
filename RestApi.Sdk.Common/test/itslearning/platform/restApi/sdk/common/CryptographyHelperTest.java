/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
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
        String expected = "80aa7dbdafe74c4fca73dbcdd6107c08";
        String input = "5c4a6404-528e-48bd-90ee-b3bea6a1e772seuvxouziedxsjyzul0qmo452009-08-29T23:43:49";
        String result = CryptographyHelper.computeHash(input);
        assertEquals(expected, result);
    }
}