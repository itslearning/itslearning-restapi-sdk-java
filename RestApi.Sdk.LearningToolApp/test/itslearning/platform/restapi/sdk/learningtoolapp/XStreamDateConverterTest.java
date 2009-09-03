/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601GregorianCalendarConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.Date;
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
    public void testConvertDateXMLToObject()
    {
        String xmlDate = "<LearningObjectInstance><ActiveToUtc>9999-12-31T23:59:59Z</ActiveToUtc></LearningObjectInstance>";
        String[] dateFormats = new String[]
        {
            "yyyyMMdd", "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd"
        };
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("LearningObjectInstance", MyTestClass.class);

        DateConverter converter = new DateConverter("yyyy-MM-dd'T'HH:mm:ss.S'Z'", dateFormats);
        xStream.registerConverter(converter);

        MyTestClass instance = (MyTestClass) xStream.fromXML(xmlDate);
        assertNotNull(instance);

    }

    public class MyTestClass
    {

        private Date ActiveToUtc;

        /**
         * @return the activeToUtc
         */
        public Date getActiveToUtc()
        {
            return ActiveToUtc;
        }

        /**
         * @param activeToUtc the activeToUtc to set
         */
        public void setActiveToUtc(Date activeToUtc)
        {
            this.ActiveToUtc = activeToUtc;
        }
    }
}