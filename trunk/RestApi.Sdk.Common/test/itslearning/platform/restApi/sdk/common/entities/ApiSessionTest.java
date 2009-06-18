/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restApi.sdk.common.entities;

import java.util.Date;
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
        instance.setLastRequestDateTimeUtc(new Date());
        String expResult = "";
        String result = instance.GetTimeStamp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ParseTimeStamp method, of class ApiSession.
     */
    @Test
    public void testParseTimeStamp() throws Exception
    {
        System.out.println("ParseTimeStamp");
        String str = "2009-06-18T15:59:17";
        Date expResult = null;
        Date result = ApiSession.ParseTimeStamp(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
     * Test of getApplicationKey method, of class ApiSession.
     */
    @Test
    public void testGetApplicationKey()
    {
        System.out.println("getApplicationKey");
        ApiSession instance = new ApiSession();
        String expResult = "";
        String result = instance.getApplicationKey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setApplicationKey method, of class ApiSession.
     */
    @Test
    public void testSetApplicationKey()
    {
        System.out.println("setApplicationKey");
        String applicationKey = "";
        ApiSession instance = new ApiSession();
        instance.setApplicationKey(applicationKey);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHash method, of class ApiSession.
     */
    @Test
    public void testGetHash()
    {
        System.out.println("getHash");
        ApiSession instance = new ApiSession();
        String expResult = "";
        String result = instance.getHash();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHash method, of class ApiSession.
     */
    @Test
    public void testSetHash()
    {
        System.out.println("setHash");
        String hash = "";
        ApiSession instance = new ApiSession();
        instance.setHash(hash);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSessionId method, of class ApiSession.
     */
    @Test
    public void testGetSessionId()
    {
        System.out.println("getSessionId");
        ApiSession instance = new ApiSession();
        String expResult = "";
        String result = instance.getSessionId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSessionId method, of class ApiSession.
     */
    @Test
    public void testSetSessionId()
    {
        System.out.println("setSessionId");
        String sessionId = "";
        ApiSession instance = new ApiSession();
        instance.setSessionId(sessionId);
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