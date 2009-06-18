/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restApi.sdk.common.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class ApiSessionTest {

    public ApiSessionTest() {
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
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of GetTimeStamp method, of class ApiSession.
     */
    @Test
    public void testGetTimeStamp()
    {
        System.out.println("GetTimeStamp");
        ApiSession instance = new ApiSession();
        instance.setLastRequestDateTimeUtc(new GregorianCalendar(2009, 5, 18, 18, 59, 17).getTime());
        String expResult = "2009-06-18T18:59:17";
        String result = instance.GetTimeStamp();
        assertEquals(expResult, result);
    }

    /**
     * Test of ParseTimeStamp method, of class ApiSession.
     */
    @Test
    public void testParseTimeStamp() throws Exception
    {
        System.out.println("ParseTimeStamp");
        String str = "2009-06-18T18:59:17";
        Calendar cal = new GregorianCalendar(2009, 5, 18, 18, 59, 17);

        Date expResult = cal.getTime();
        Date result = ApiSession.ParseTimeStamp(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of CreateApiSession method, of class ApiSession.
     */
    @Test
    public void testCreateApiSession()
    {
        System.out.println("CreateApiSession");
        String sessionId = "";
        ApiSession expResult = null;
        ApiSession result = ApiSession.CreateApiSession(sessionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

   
    /**
     * Test of getLastRequestDateTimeUtc method, of class ApiSession.
     */
    @Test
    public void testGetLastRequestDateTimeUtc()
    {
        System.out.println("getLastRequestDateTimeUtc");
        ApiSession instance = new ApiSession();
        Date expResult = null;
        Date result = instance.getLastRequestDateTimeUtc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLastRequestDateTimeUtc method, of class ApiSession.
     */
    @Test
    public void testSetLastRequestDateTimeUtc()
    {
        System.out.println("setLastRequestDateTimeUtc");
        Date lastRequestDateTimeUtc = null;
        ApiSession instance = new ApiSession();
        instance.setLastRequestDateTimeUtc(lastRequestDateTimeUtc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}