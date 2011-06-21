/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.RequestParamsHandler;
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
        requestParameterMap.put("PAgeUrl", new String[]{ "http://www.vg.no"});
        requestParameterMap.put("LearningObjectId", new String[]{ "1"});
        ViewLearningToolRequestParams newInstance = (ViewLearningToolRequestParams) RequestParamsHandler.getParams(requestParameterMap, ViewLearningToolRequestParams.class);
        assertNotNull(newInstance);
        assertEquals(newInstance.getPageUrl(), ((String[]) requestParameterMap.get("PAgeUrl"))[0]);
        assertEquals(newInstance.getLearningObjectId().toString(), ((String[]) requestParameterMap.get("LearningObjectId"))[0]);
    }

}