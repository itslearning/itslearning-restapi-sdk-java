/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.ConverterRegistry;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601GregorianCalendarConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionProviderWrapper;
import com.thoughtworks.xstream.core.DefaultConverterLookup;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.CachingMapper;
import com.thoughtworks.xstream.mapper.Mapper;
import java.io.InputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
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

    final ConverterLookup lookup = new DefaultConverterLookup();
    ConverterRegistry registry;

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

    @Test
    public void testConvertDateXMLToObjectWithDom4j() throws DocumentException, ParseException
    {
        String xmlDate = "<LearningObjectInstance " +
                "xmlns=\"http://schemas.datacontract.org/2004/07/Itslearning.Platform.RestApi.Sdk.LearningToolApp.Entities\" " +
                "xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<ActiveToUtc>9999-12-31T23:59:59Z</ActiveToUtc><CreatedUtc i:nil=\"true\"></LearningObjectInstance>";
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        SAXReader reader = new SAXReader(false);
        Document doc = reader.read(new StringReader(xmlDate));

        MyTestClass tc = parseDocumentToTestClass(doc);

    }

    private MyTestClass parseDocumentToTestClass(Document doc) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        MyTestClass myTestClass = null;
        Element root = doc.getRootElement();
        if(root.getName().equals("LearningObjectInstance")){
            myTestClass = new MyTestClass();
            Node node = root.selectSingleNode("ActiveToUtc");
            if(node.hasContent()){
                myTestClass.setActiveToUtc(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode("CreatedUtc");
            if(node.hasContent()){
                
            }
        }
        return myTestClass;
    }

    /*@Test
    public void testConvertNullDateXMLToDate()
    {
    setUpRegistry();
    String xmlDate = "<LearningObjectInstance><ActiveToUtc i:nil=\"true\" /></LearningObjectInstance>";
    String[] dateFormats = new String[]
    {
    "yyyyMMdd", "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd"
    };
    //XStream xStream = new XStream(new DomDriver());
    //Mapper m = xStream.getMapper();
    XStream xStream = new XStream(new PureJavaReflectionProvider(), new DomDriver(),
    ClassLoader.getSystemClassLoader(),  (Mapper)null, lookup, registry);
    xStream.alias("LearningObjectInstance", MyTestClass.class);

    DateConverter converter = new DateConverter("yyyy-MM-dd'T'HH:mm:ss.S'Z'", dateFormats);
    xStream.registerConverter(converter);

    MyTestClass instance = (MyTestClass) xStream.fromXML(xmlDate);
    assertNotNull(instance);
    }*/
    private void setUpRegistry()
    {
        registry = new ConverterRegistry()
        {

            public void registerConverter(final Converter converter, int priority)
            {
                ((ConverterRegistry) lookup).registerConverter(new Converter()
                {

                    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
                    {
                        return reader.getAttribute("i:nil") != null ? null : converter.unmarshal(reader, context);
                    }

                    public void marshal(Object arg0, HierarchicalStreamWriter arg1, MarshallingContext arg2)
                    {
                    }

                    public boolean canConvert(Class arg0)
                    {
                        return true;
                    }
                }, 0);
            }
        };
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