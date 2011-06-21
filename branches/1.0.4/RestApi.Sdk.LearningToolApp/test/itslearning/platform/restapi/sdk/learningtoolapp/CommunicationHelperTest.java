/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.Settings.IApplicationSettings;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restApi.sdk.common.entities.LearningObjectInstancePermissions;
import itslearning.platform.restApi.sdk.common.entities.SchoolInfo;
import itslearning.platform.restApi.sdk.common.entities.UserInfo;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kschmidt
 */
public class CommunicationHelperTest {

    public CommunicationHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of buildSchoolInfoList method, of class CommunicationHelper.
     */
    @Test
    public void testBuildSchoolInfoList() throws Exception {
	System.out.println("buildSchoolInfoList");
	String schoolIdParameter = "123|Newcastle\\,upon Tyne,124| abc, 127| London HS1,";

	SchoolInfo schoolInfo1 = new SchoolInfo();
	schoolInfo1.setSchoolId(123);
	schoolInfo1.setLegalId("Newcastle,upon Tyne");

	SchoolInfo schoolInfo2 = new SchoolInfo();
	schoolInfo2.setSchoolId(124);
	schoolInfo2.setLegalId("abc");

	SchoolInfo schoolInfo3 = new SchoolInfo();
	schoolInfo3.setSchoolId(127);
	schoolInfo3.setLegalId("London HS1");

	ArrayList<SchoolInfo> expResult = new ArrayList<SchoolInfo>(3);
	expResult.add(schoolInfo1);
	expResult.add(schoolInfo2);
	expResult.add(schoolInfo3);

	ArrayList<SchoolInfo> result = CommunicationHelper.buildSchoolInfoList(schoolIdParameter);

	assertEquals(expResult != null, result != null);
	assertArrayEquals(expResult.toArray(), result.toArray());
	
    }

}