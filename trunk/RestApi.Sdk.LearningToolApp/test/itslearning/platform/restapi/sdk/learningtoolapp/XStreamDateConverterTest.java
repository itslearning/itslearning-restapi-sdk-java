/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;
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
public class XStreamDateConverterTest
{

    public XStreamDateConverterTest()
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

    @Test
    public void testConvertLearningObjectInstanceXMLToObjectWithDom4j() throws DocumentException, ParseException
    {
        String xmlDate = "<LearningObjectInstance " +
                "xmlns=\"http://schemas.datacontract.org/2004/07/Itslearning.Platform.RestApi.Sdk.LearningToolApp.Entities\" " +
                "xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<ActiveToUtc>9999-12-31T23:59:59Z</ActiveToUtc><CreatedUtc i:nil=\"true\" />" +
                "<Title></Title></LearningObjectInstance>";

        SAXReader reader = new SAXReader();

        Document doc = reader.read(new StringReader(xmlDate));
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", "http://schemas.datacontract.org/2004/07/Itslearning.Platform.RestApi.Sdk.LearningToolApp.Entities")));
        LearningObjectInstance tc = parseDocumentToTestClass(doc);
        assertNotNull(tc.getActiveToUTC());
    }

    private LearningObjectInstance parseDocumentToTestClass(Document doc) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        LearningObjectInstance myTestClass = null;
        Element root = doc.getRootElement();

        if (root.getName().equals("LearningObjectInstance"))
        {
            myTestClass = new LearningObjectInstance();
            Node node = root.selectSingleNode("//loi:LearningObjectInstance/loi:ActiveToUtc");

            if (node.hasContent())
            {
                myTestClass.setActiveToUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode("//loi:LearningObjectInstance/loi:CreatedUtc");
            if (node.hasContent())
            {
                myTestClass.setCreatedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode("//loi:LearningObjectInstance/loi:Title");
            if (node.hasContent())
            {
                myTestClass.setTitle(node.getStringValue());
            }
        }
        return myTestClass;
    }
}