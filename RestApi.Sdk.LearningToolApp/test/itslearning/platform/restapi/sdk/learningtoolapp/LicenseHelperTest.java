/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp;

import java.util.Dictionary;
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
public class LicenseHelperTest {

    public LicenseHelperTest() {
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

    @Test
    public void testGetLicenseIds()
    {
        String licenseIds = "1,2,3";
        String externalLicenseIds = "i,a\\\\m,external\\,";
        Dictionary<Integer, String> licenses = LicenseHelper.getLicenseIds(licenseIds, externalLicenseIds);
        assertNotNull(licenses);
        assertEquals("i", licenses.get(new Integer(1)));
        assertEquals("a\\m", licenses.get(new Integer(2)));
        assertEquals("external,", licenses.get(new Integer(3)));
    }

}