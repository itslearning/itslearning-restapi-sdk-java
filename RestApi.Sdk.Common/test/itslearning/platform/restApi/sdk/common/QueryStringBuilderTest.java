/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

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
public class QueryStringBuilderTest
{

    public QueryStringBuilderTest()
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
     * Test of getQueryString method, of class QueryStringBuilder.
     */
    @Test
    public void testGetQueryString()
    {
        System.out.println("getQueryString");
        QueryStringBuilder instance = new QueryStringBuilder("http://MySite.com", true);
        instance.AddParameter("oy", "yo");
        instance.AddParameter("something", "somethingElse");
        String expResult = "http://MySite.com?oy=yo&amp;something=somethingElse";
        String result = instance.getQueryString();
        assertTrue(expResult.compareTo(result) == 0);
    }

}