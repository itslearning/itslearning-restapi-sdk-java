/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restApi.sdk.common.exceptions;

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
public class ItslExceptionTest {

    public ItslExceptionTest() {
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
     * Test of toString method, of class ItslException.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        ItslException instance = new ItslException(ItslExceptionCode.ObjectNotFound);
        String expResult ="ObjectNotFound: itslearning.platform.restApi.sdk.common.exceptions.ItslException";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void testToString2(){
        System.out.println("toString2");
        Integer i = new Integer("2");

        ObjectNotExistOrAccessDeniedException ex = new ObjectNotExistOrAccessDeniedException(i.getClass(), "");
        assertNotNull(ex.toString());
    }

}