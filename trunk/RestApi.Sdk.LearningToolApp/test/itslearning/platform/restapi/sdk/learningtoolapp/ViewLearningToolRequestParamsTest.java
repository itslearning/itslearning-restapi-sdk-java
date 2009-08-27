/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.RequestParamsHandler;
import itslearning.platform.restApi.sdk.common.entities.Constants.LearningObjectInstancePermissions;
import java.util.HashMap;
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
public class ViewLearningToolRequestParamsTest {

    public ViewLearningToolRequestParamsTest() {
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
     * RequestParamsHandler test
     */
    @Test
    public void testRequestParamsHandler()
    {
        ViewLearningToolRequestParams instance = new ViewLearningToolRequestParams();
        instance.setSignature("");
        HashMap requestParameterMap = new HashMap();
        requestParameterMap.put("PAgeUrl", "http://www.vg.no");
        ViewLearningToolRequestParams newInstance = (ViewLearningToolRequestParams) RequestParamsHandler.getParams(requestParameterMap, ViewLearningToolRequestParams.class);
        assertNotNull(newInstance);
        assertEquals(newInstance.getPageUrl(),requestParameterMap.get("PAgeUrl"));
        
    }

}