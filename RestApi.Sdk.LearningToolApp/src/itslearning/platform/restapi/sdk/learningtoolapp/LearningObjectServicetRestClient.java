package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.ExceptionHandler;
import itslearning.platform.restApi.sdk.common.ThreadSafeDateFormat;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restApi.sdk.common.entities.Constants.OrganisationType;
import itslearning.platform.restApi.sdk.common.entities.Constants.SimpleStatusType;
import itslearning.platform.restApi.sdk.common.entities.Constants.EducationSegment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AppLicense;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Assessment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatus;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatusItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.EntityConstants;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUserReport;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Notification;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Organisation;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Site;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import javax.xml.ws.http.HTTPException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

/**
 * Bundled implementation of the ILearningObjectServiceRestClient
 * @author Amund Trovåg
 */
public class LearningObjectServicetRestClient implements ILearningObjectServiceRestClient
{

    private HttpClient _httpClient = new HttpClient();
    private static ThreadSafeDateFormat sdf = new ThreadSafeDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static
    {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    private ApiSession _apiSession;
    private String _baseUri;
    private String _sharedSecret;

    /**
     *
     * @param apiSession
     * @param baseUri the baseUri of the restApi, e.g. http://betarest.itslearning.com depending on the environment. This must be set by client
     * in order to communicate.
     */
    public LearningObjectServicetRestClient(ApiSession apiSession, String sharedSecret, String baseUri)
    {
        if (apiSession == null || baseUri == null || baseUri.isEmpty())
        {
            throw new IllegalArgumentException("The client needs an apisession and a baseUri to work");
        }
        this._apiSession = apiSession;
        this._baseUri = baseUri;
        this._sharedSecret = sharedSecret;
    }

    protected enum HttpMethodType
    {

        GET, PUT, POST, DELETE;
    }

    private List<AssessmentStatusItem> deserializeXMLToListOfAssessmentStatusItems(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<AssessmentStatusItem> result = new ArrayList<AssessmentStatusItem>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessmentStatusItem";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:AssessmentStatusItem");

        for (Node node : nodes)
        {
            AssessmentStatusItem assessmentStatusItem = new AssessmentStatusItem();
            Node n = node.selectSingleNode("loi:AssessmentStatusId");
            if (n.hasContent())
            {
                assessmentStatusItem.setAssessmentStatusId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:AssessmentStatusItemId");
            if (n.hasContent())
            {
                assessmentStatusItem.setAssessmentStatusItemId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Title");
            if (n.hasContent())
            {
                assessmentStatusItem.setTitle(n.getStringValue());
            }
            result.add(assessmentStatusItem);
        }

        return result;
    }

    /**
     * xml looks like this
<EntityList xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
  <CurrentPageIndex>0</CurrentPageIndex>
  <EntityArray>
    <Organization>
      <HierarchyId>1</HierarchyId>
      <LegalId>FK</LegalId>
      <Title>Fylkeskommune</Title>
      <Type>Site</Type>
    </Organization>
    <Organization>
      <HierarchyId>6</HierarchyId>
      <LegalId>S-B</LegalId>
      <Title>School B</Title>
      <Type>School</Type>
    </Organization>
  </EntityArray>
  <PageSize>2</PageSize>
  <Total>2</Total>
</EntityList>
     *
     * @param responseBodyAsStream
     * @return
     */
    private List<Organisation> deserializeXMLToOrganisations(InputStream xmlStream) throws DocumentException
    {
        List<Organisation> result = new ArrayList<Organisation>();
        
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//org:ArrayOfOrganization";
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("org", doc.getRootElement().getNamespaceURI())));

        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/org:Organization");

        for(Node n : nodes)
        {
            Organisation organisation = new Organisation();
            Node node = n.selectSingleNode("org:HierarchyId");
            if(node.hasContent()){
                organisation.setHierarchyId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("org:LegalId");
            if(node.hasContent()){
                organisation.setLegalId(node.getStringValue());
            }
            node = n.selectSingleNode("org:Title");
            if(node.hasContent()){
                organisation.setTitle(node.getStringValue());
            }
            node = n.selectSingleNode("org:Type");
            if(node.hasContent())
            {
                try
                {
                    organisation.setType(OrganisationType.valueOf(node.getStringValue()));
                }
                catch(IllegalArgumentException iea){
                    organisation.setType(OrganisationType.Unknown);
                }
            }
            result.add(organisation);
        }

        return result;
    }


    private List<AppLicense> deserializeXMLToAppLicenses(InputStream xmlStream) throws DocumentException {
        List<AppLicense> result = new ArrayList<AppLicense>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAppLicense";
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));

        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:AppLicense");

        for(Node n : nodes)
        {
            AppLicense appLicense = new AppLicense();
            Node node = n.selectSingleNode("loi:LicenseId");
            if(node.hasContent()){
                appLicense.setLicenseId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:ExternalLicenseId");
            if(node.hasContent()){
                appLicense.setExternalLicenseId(node.getStringValue());
            }
            result.add(appLicense);
        }
        return result;
    }
    
    private Site deserializeXMLToSite(InputStream xmlStream) throws ParseException, DocumentException
    {
        Site site = null;

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);
        String lElem = "//loi:Site";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();
        if (root.getName().equals("Site"))
        {
            site = new Site();

            Node node = root.selectSingleNode(lElem + "/loi:Segment");
            if(node.hasContent())
            {
                try
                {
                    site.setSegment(EducationSegment.valueOf(node.getStringValue()));
                }
                catch(IllegalArgumentException iea){
                    site.setSegment(EducationSegment.Other);
                }
            }
            node = root.selectSingleNode(lElem + "/loi:CountryCode");
            if (node.hasContent())
            {
                site.setCountryCode(node.getStringValue());
            }
        }
        return site;
    }


    private List<LearningObjectInstanceUserReport> deserializeXMLToListOfLearningObjectInstanceUserReport(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<LearningObjectInstanceUserReport> result = new ArrayList<LearningObjectInstanceUserReport>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfLearningObjectInstanceUserReport";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:LearningObjectInstanceUserReport");

        for(Node n : nodes){
            LearningObjectInstanceUserReport singleReport = new LearningObjectInstanceUserReport();
            Node node = n.selectSingleNode("loi:AssessmentItemId");
            if (node.hasContent())
            {
                singleReport.setAssessmentItemId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:AssessmentItemTitle");
            if (node.hasContent())
            {
                singleReport.setAssessmentItemTitle(node.getStringValue());
            }
            node = n.selectSingleNode("loi:AssessmentStatusItemId");
            if (node.hasContent())
            {
                singleReport.setAssessmentStatusItemId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:AssessmentStatusItemTitle");
            if (node.hasContent())
            {
                singleReport.setAssessmentStatusItemTitle(node.getStringValue());
            }
            node = n.selectSingleNode("loi:Comment");
            if (node.hasContent())
            {
                singleReport.setComment(node.getStringValue());
            }
            node = n.selectSingleNode("loi:FirstName");
            if (node.hasContent())
            {
                singleReport.setFirstName(node.getStringValue());
            }
            node = n.selectSingleNode("loi:LastName");
            if (node.hasContent())
            {
                singleReport.setLastName(node.getStringValue());
            }
            node = n.selectSingleNode("loi:NumberOfTimesRead");
            if (node.hasContent())
            {
                singleReport.setNumberOfTimesRead(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:SimplePercentScore");
            if (node.hasContent())
            {
                singleReport.setSimplePercentScore(Double.parseDouble(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:SimpleStatus");
            if (node.hasContent())
            {
                singleReport.setSimpleStatus(SimpleStatusType.valueOf(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:UserId");
            if (node.hasContent())
            {
                singleReport.setUserId(Integer.parseInt(node.getStringValue()));
            }

            // All following nodes are extended data which will only be sent if the app is allowed to receive them.
            // If one of the fields is there, all will be. 
            node = root.selectSingleNode(lElem + "/loi:Custom1");
            if( node != null)
            {
                if (node.hasContent())
                {
                    singleReport.setCustom1(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom2");
                if (node.hasContent())
                {
                    singleReport.setCustom2(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom3");
                if (node.hasContent())
                {
                    singleReport.setCustom3(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom4");
                if (node.hasContent())
                {
                    singleReport.setCustom4(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom5");
                if (node.hasContent())
                {
                    singleReport.setCustom5(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom1Id");
                if (node.hasContent())
                {
                    singleReport.setCustom1Id(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom2Id");
                if (node.hasContent())
                {
                    singleReport.setCustom2Id(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom3Id");
                if (node.hasContent())
                {
                    singleReport.setCustom3Id(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom4Id");
                if (node.hasContent())
                {
                    singleReport.setCustom4Id(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Custom5Id");
                if (node.hasContent())
                {
                    singleReport.setCustom5Id(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Mobile");
                if (node.hasContent())
                {
                    singleReport.setMobile(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:SyncKey");
                if (node.hasContent())
                {
                    singleReport.setSyncKey(node.getStringValue());
                }
                node = root.selectSingleNode(lElem + "/loi:Email");
                if (node.hasContent())
                {
                    singleReport.setEmail(node.getStringValue());
                }
            }
            // End of extended data.

            result.add(singleReport);
        }

        return result;
    }

    private List<AssessmentStatus> deserializeXMLToListOfPossibleAssessmentStatuses(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<AssessmentStatus> result = new ArrayList<AssessmentStatus>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessmentStatus";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:AssessmentStatus");

        for (Node node : nodes)
        {
            AssessmentStatus assessmentStatus = new AssessmentStatus();
            Node n = node.selectSingleNode("loi:AssessmentStatusId");
            if (n.hasContent())
            {
                assessmentStatus.setAssessmentStatusId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Title");
            if (n.hasContent())
            {
                assessmentStatus.setTitle(n.getStringValue());
            }
            result.add(assessmentStatus);
        }

        return result;
    }

    private LearningObjectInstanceUserReport deserializeXMLToLearningObjectInstanceUserReport(InputStream xmlStream) throws ParseException, DocumentException
    {
        LearningObjectInstanceUserReport result = new LearningObjectInstanceUserReport();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:LearningObjectInstanceUserReport";
        
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();
        if (root.getName().equals("LearningObjectInstanceUserReport"))
        {
            result = new LearningObjectInstanceUserReport();

            Node node = root.selectSingleNode(lElem + "/loi:AssessmentItemId");
            if (node.hasContent())
            {
                result.setAssessmentItemId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:AssessmentItemTitle");
            if (node.hasContent())
            {
                result.setAssessmentItemTitle(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:AssessmentStatusItemId");
            if (node.hasContent())
            {
                result.setAssessmentStatusItemId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:AssessmentStatusItemTitle");
            if (node.hasContent())
            {
                result.setAssessmentStatusItemTitle(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Comment");
            if (node.hasContent())
            {
                result.setComment(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:FirstName");
            if (node.hasContent())
            {
                result.setFirstName(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:LastName");
            if (node.hasContent())
            {
                result.setLastName(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:NumberOfTimesRead");
            if (node.hasContent())
            {
                result.setNumberOfTimesRead(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:SimplePercentScore");
            if (node.hasContent())
            {
                result.setSimplePercentScore(Double.parseDouble(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:SimpleStatus");
            if (node.hasContent())
            {
                result.setSimpleStatus(SimpleStatusType.valueOf(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:UserId");
            if (node.hasContent())
            {
                result.setUserId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:Custom1");
            if (node.hasContent())
            {
                result.setCustom1(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom2");
            if (node.hasContent())
            {
                result.setCustom2(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom3");
            if (node.hasContent())
            {
                result.setCustom3(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom4");
            if (node.hasContent())
            {
                result.setCustom4(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom5");
            if (node.hasContent())
            {
                result.setCustom5(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom1Id");
            if (node.hasContent())
            {
                result.setCustom1Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom2Id");
            if (node.hasContent())
            {
                result.setCustom2Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom3Id");
            if (node.hasContent())
            {
                result.setCustom3Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom4Id");
            if (node.hasContent())
            {
                result.setCustom4Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom5Id");
            if (node.hasContent())
            {
                result.setCustom5Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Mobile");
            if (node.hasContent())
            {
                result.setMobile(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:SyncKey");
            if (node.hasContent())
            {
                result.setSyncKey(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Email");
            if (node.hasContent())
            {
                result.setEmail(node.getStringValue());
            }
        }

        return result;
    }

    private String serializeLearningObjectInstanceUserReportToXML(LearningObjectInstanceUserReport userReport)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("LearningObjectInstanceUserReport");
        root.setQName(new QName("LearningObjectInstanceUserReport", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));

        if (userReport.getAssessmentItemId() != null)
        {
            root.addElement("AssessmentItemId").addText(userReport.getAssessmentItemId().toString());
        }
        if (userReport.getAssessmentItemTitle() != null)
        {
            root.addElement("AssessmentItemTitle").addText(userReport.getAssessmentItemTitle());
        }
        if (userReport.getAssessmentStatusItemId() != null)
        {
            root.addElement("AssessmentStatusItemId").addText(userReport.getAssessmentStatusItemId().toString());
        }
        if (userReport.getAssessmentStatusItemTitle() != null)
        {
            root.addElement("AssessmentStatusItemTitle").addText(userReport.getAssessmentStatusItemTitle());
        }
        if (userReport.getComment() != null)
        {
            root.addElement("Comment").addText(userReport.getComment());
        }
        if (userReport.getFirstName() != null)
        {
            root.addElement("FirstName").addText(userReport.getFirstName());
        }
        if (userReport.getLastName() != null)
        {
            root.addElement("LastName").addText(userReport.getLastName());
        }
        if (userReport.getNumberOfTimesRead() != null)
        {
            root.addElement("NumberOfTimesRead").addText(userReport.getNumberOfTimesRead().toString());
        }
        if (userReport.getSimplePercentScore() != null)
        {
            root.addElement("SimplePercentScore").addText(userReport.getSimplePercentScore().toString());
        }
        if (userReport.getSimpleStatus() != null)
        {
            root.addElement("SimpleStatus").addText(userReport.getSimpleStatus().toString());
        }
        if (userReport.getEmail() != null)
        {
            root.addElement("Email").addText(userReport.getEmail().toString());
        }
        if (userReport.getMobile() != null)
        {
            root.addElement("Mobile").addText(userReport.getMobile().toString());
        }
        if (userReport.getSyncKey() != null)
        {
            root.addElement("SyncKey").addText(userReport.getSyncKey().toString());
        }
        if (userReport.getCustom1() != null)
        {
            root.addElement("Custom1").addText(userReport.getCustom1().toString());
        }
        if (userReport.getCustom2() != null)
        {
            root.addElement("Custom2").addText(userReport.getCustom2().toString());
        }
        if (userReport.getCustom3() != null)
        {
            root.addElement("Custom3").addText(userReport.getCustom3().toString());
        }
        if (userReport.getCustom4() != null)
        {
            root.addElement("Custom4").addText(userReport.getCustom4().toString());
        }
        if (userReport.getCustom5() != null)
        {
            root.addElement("Custom5").addText(userReport.getCustom5().toString());
        }
        if (userReport.getCustom1Id() != null)
        {
            root.addElement("Custom1Id").addText(userReport.getCustom1Id().toString());
        }
        if (userReport.getCustom2Id() != null)
        {
            root.addElement("Custom2Id").addText(userReport.getCustom2Id().toString());
        }
        if (userReport.getCustom3Id() != null)
        {
            root.addElement("Custom3Id").addText(userReport.getCustom3Id().toString());
        }
        if (userReport.getCustom4Id() != null)
        {
            root.addElement("Custom4Id").addText(userReport.getCustom4Id().toString());
        }
        if (userReport.getCustom5Id() != null)
        {
            root.addElement("Custom5Id").addText(userReport.getCustom5Id().toString());
        }
            
        

        root.addElement("UserId").addText("" + userReport.getUserId());

        return root.asXML();
    }

    private String serializeNotificationToXML(Notification notification)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("Notification");
        root.setQName(new QName("Notification", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));

        if (notification.getLaunchParameter() != null)
        {
            root.addElement("LaunchParameter").addText(notification.getLaunchParameter().toString());
        }
        if (notification.getLocalizedMessages() != null)
        {
            Element n = root.addElement("LocalizedMessages");
            n.add(new Namespace("a", "http://schemas.microsoft.com/2003/10/Serialization/Arrays"));

            HashMap<String,String> messages = notification.getLocalizedMessages();
            for(String lang : messages.keySet())
            {
                Element messageKeyValuePair = n.addElement("a:KeyValueOfstringstring");
                messageKeyValuePair.addElement("a:Key").addText(lang);
                messageKeyValuePair.addElement("a:Value").addText(messages.get(lang));
            }
        }
        if( notification.getMessage() != null )
        {
            root.addElement("Message").addText(notification.getMessage());
        }
        if( notification.getReciverPermission() != null )
        {
            root.addElement("ReciverPermission").addText(notification.getReciverPermission().toString());
        }

        return root.asXML();
    }

    private String serializeLearningObjectInstanceUserReportsToXML(List<LearningObjectInstanceUserReport> userReports)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("ArrayOfLearningObjectInstanceUserReport");
        root.setQName(new QName("ArrayOfLearningObjectInstanceUserReport", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));

        for (LearningObjectInstanceUserReport userReport : userReports)
        {
            Element n = root.addElement("LearningObjectInstanceUserReport");
            if (userReport.getAssessmentItemId() != null)
            {
                n.addElement("AssessmentItemId").addText(userReport.getAssessmentItemId().toString());
            }
            if (userReport.getAssessmentItemTitle() != null)
            {
                n.addElement("AssessmentItemTitle").addText(userReport.getAssessmentItemTitle());
            }
            if (userReport.getAssessmentStatusItemId() != null)
            {
                n.addElement("AssessmentStatusItemId").addText(userReport.getAssessmentStatusItemId().toString());
            }
            if (userReport.getAssessmentStatusItemTitle() != null)
            {
                n.addElement("AssessmentStatusItemTitle").addText(userReport.getAssessmentStatusItemTitle());
            }
            if (userReport.getComment() != null)
            {
                n.addElement("Comment").addText(userReport.getComment());
            }
            if (userReport.getFirstName() != null)
            {
                n.addElement("FirstName").addText(userReport.getFirstName());
            }
            if (userReport.getLastName() != null)
            {
                n.addElement("LastName").addText(userReport.getLastName());
            }
            if (userReport.getNumberOfTimesRead() != null)
            {
                n.addElement("NumberOfTimesRead").addText(userReport.getNumberOfTimesRead().toString());
            }
            if (userReport.getSimplePercentScore() != null)
            {
                n.addElement("SimplePercentScore").addText(userReport.getSimplePercentScore().toString());
            }
            if (userReport.getSimpleStatus() != null)
            {
                n.addElement("SimpleStatus").addText(userReport.getSimpleStatus().toString());
            }
            if (userReport.getEmail() != null)
            {
                n.addElement("Email").addText(userReport.getEmail().toString());
            }
            if (userReport.getMobile() != null)
            {
                n.addElement("Mobile").addText(userReport.getMobile().toString());
            }
            if (userReport.getSyncKey() != null)
            {
                n.addElement("SyncKey").addText(userReport.getSyncKey().toString());
            }
            if (userReport.getCustom1() != null)
            {
                n.addElement("Custom1").addText(userReport.getCustom1().toString());
            }
            if (userReport.getCustom2() != null)
            {
                n.addElement("Custom2").addText(userReport.getCustom2().toString());
            }
            if (userReport.getCustom3() != null)
            {
                n.addElement("Custom3").addText(userReport.getCustom3().toString());
            }
            if (userReport.getCustom4() != null)
            {
                n.addElement("Custom4").addText(userReport.getCustom4().toString());
            }
            if (userReport.getCustom5() != null)
            {
                n.addElement("Custom5").addText(userReport.getCustom5().toString());
            }
            if (userReport.getCustom1Id() != null)
            {
                n.addElement("Custom1Id").addText(userReport.getCustom1Id().toString());
            }
            if (userReport.getCustom2Id() != null)
            {
                n.addElement("Custom2Id").addText(userReport.getCustom2Id().toString());
            }
            if (userReport.getCustom3Id() != null)
            {
                n.addElement("Custom3Id").addText(userReport.getCustom3Id().toString());
            }
            if (userReport.getCustom4Id() != null)
            {
                n.addElement("Custom4Id").addText(userReport.getCustom4Id().toString());
            }
            if (userReport.getCustom5Id() != null)
            {
                n.addElement("Custom5Id").addText(userReport.getCustom5Id().toString());
            }
            
            n.addElement("UserId").addText("" + userReport.getUserId());
        }
        return root.asXML();
    }

    private String serializeLearningObjectInstanceToXML(LearningObjectInstance instance)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("LearningObjectInstance");
        root.setQName(new QName("LearningObjectInstance", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));

        if (instance.getActiveFromUTC() != null)
        {
            String activeFromUTCText = sdf.format(instance.getActiveFromUTC());
            root.addElement("ActiveFromUtc").addText(activeFromUTCText);
        }
        if (instance.getActiveToUTC() != null)
        {
            String activeToUTCText = sdf.format(instance.getActiveToUTC());
            root.addElement("ActiveToUtc").addText(activeToUTCText);
        }

        root.addElement("AssessmentId").addText(instance.getAssessmentId().toString());
        root.addElement("AssessmentStatusId").addText(instance.getAssessmentStatusId().toString());
        root.addElement("CreatedByUserId").addText("" + instance.getCreatedByUserId());

        if (instance.getCreatedUTC() != null)
        {
            String createdUTCText = sdf.format(instance.getCreatedUTC());
            root.addElement("CreatedUtc").addText(createdUTCText);
        }
        root.addElement("CourseCode").addText("" + instance.getCourseCode());
        root.addElement("CourseId").addText("" + instance.getCourseId());
        root.addElement("CourseSyncKey").addText("" + instance.getCourseSyncKey());
        if (instance.getDeadLineUTC() != null)
        {
            String deadlineUTCText = sdf.format(instance.getDeadLineUTC());
            root.addElement("DeadlineUtc").addText(deadlineUTCText);
        }
        
        root.addElement("IsObligatory").addText(String.valueOf(instance.isIsObligatory()));

        root.addElement("LearningObjectId").addText("" + instance.getLearningObjectId());
        root.addElement("LearningObjectInstanceId").addText("" + instance.getLearningObjectInstanceId());

        if (instance.getModifiedUTC() != null)
        {
            String modifiedUTCText = sdf.format(instance.getModifiedUTC());
            root.addElement("ModifiedUtc").addText(modifiedUTCText);
        }
        
        root.addElement("Title").addText(instance.getTitle());


        return root.asXML();
    }

    private List<AssessmentItem> deserializeXMLToListOfAssessmentItems(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<AssessmentItem> result = new ArrayList<AssessmentItem>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessmentItem";
        
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:AssessmentItem");

        for (Node node : nodes)
        {
            AssessmentItem assessmentItem = new AssessmentItem();
            Node n = node.selectSingleNode("loi:AssessmentId");
            if (n.hasContent())
            {
                assessmentItem.setAssessmentId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:AssessmentItemId");
            if (n.hasContent())
            {
                assessmentItem.setAssessmentItemId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Description");
            if (n.hasContent())
            {
                assessmentItem.setDescription(n.getStringValue());
            }
            n = node.selectSingleNode("loi:PercentFromAndIncl");
            if (n.hasContent())
            {
                assessmentItem.setPercentFromAndIncl(Double.parseDouble(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:PercentTo");
            if (n.hasContent())
            {
                assessmentItem.setPercentTo(Double.parseDouble(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Title");
            if (n.hasContent())
            {
                assessmentItem.setTitle(n.getStringValue());
            }
            result.add(assessmentItem);
        }
        return result;
    }

    private List<Assessment> deserializeXMLToListOfAssessments(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<Assessment> result = new ArrayList<Assessment>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessment";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:Assessment");

        for (Node node : nodes)
        {
            Assessment assessment = new Assessment();
            Node n = node.selectSingleNode("loi:AssessmentId");
            if (n.hasContent())
            {
                assessment.setAssessmentId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Description");
            if (n.hasContent())
            {
                assessment.setDescription(n.getStringValue());
            }
            n = node.selectSingleNode("loi:Title");
            if (n.hasContent())
            {
                assessment.setTitle(n.getStringValue());
            }
            result.add(assessment);
        }
        return result;
    }

    private LearningObjectInstance deserializeXMLToLearningObjectInstance(InputStream xmlStream) throws ParseException, DocumentException
    {
        LearningObjectInstance loi = null;

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);
        String lElem = "//loi:LearningObjectInstance";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();
        if (root.getName().equals("LearningObjectInstance"))
        {
            loi = new LearningObjectInstance();

            Node node = root.selectSingleNode(lElem + "/loi:ActiveToUtc");
            if (node.hasContent())
            {
                loi.setActiveToUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:DeadlineUtc");
            if (node.hasContent())
            {
                loi.setDeadLineUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:ActiveFromUtc");
            if (node.hasContent())
            {
                loi.setActiveFromUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CreatedUtc");
            if (node.hasContent())
            {
                loi.setCreatedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:ModifiedUtc");
            if (node.hasContent())
            {
                loi.setModifiedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:Title");
            if (node.hasContent())
            {
                loi.setTitle(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:LearningObjectInstanceId");
            if (node.hasContent())
            {
                loi.setLearningObjectInstanceId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:LearningObjectId");
            if (node.hasContent())
            {
                loi.setLearningObjectId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:ModifiedUtc");
            if (node.hasContent())
            {
                loi.setModifiedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CreatedUtc");
            if (node.hasContent())
            {
                loi.setCreatedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CreatedByUserId");
            if (node.hasContent())
            {
                loi.setCreatedByUserId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:IsObligatory");
            if (node.hasContent())
            {
                loi.setIsObligatory(Boolean.parseBoolean(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:AssessmentId");
            if (node.hasContent())
            {
                loi.setAssessmentId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:AssessmentStatusId");
            if (node.hasContent())
            {
                loi.setAssessmentStatusId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CourseCode");
            if (node.hasContent())
            {
                loi.setCourseCode(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:CourseId");
            if (node.hasContent())
            {
                loi.setCourseId(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:CourseSyncKey");
            if (node.hasContent())
            {
                loi.setCourseSyncKey(node.getStringValue());
            }
            
        }
        return loi;
    }

    /**
     * Initializes the http client with correct httpmethod. Adds Authorization header to request.
     * @param client
     * @param uri
     * @param methodType
     * @return
     */
    protected HttpMethod getInitializedHttpMethod(HttpClient client, String uri, HttpMethodType methodType)
    {
        Calendar now = GregorianCalendar.getInstance();
        _apiSession.setLastRequestDateTimeUtc(new Date(now.getTimeInMillis()));
        _apiSession.setHash(CryptographyHelper.computeHash(_apiSession, _sharedSecret));

        String authHeader = AuthorizationHelper.toAuthorizationHeader(_apiSession);

        HttpMethod method;
        switch (methodType)
        {
            case GET:
                method = new GetMethod(uri);
                break;
            case PUT:
                method = new PutMethod(uri);
                method.setRequestHeader("Content-Type", "text/xml");
                break;
            case POST:
                method = new PostMethod(uri);
                method.setRequestHeader("Content-Type", "text/xml");
                break;
            case DELETE:
                method = new DeleteMethod(uri);
                break;
            default:
                method = new GetMethod(uri);
        }
        HttpParams params = DefaultHttpParams.getDefaultParams();
        method.setRequestHeader("Authorization", authHeader);
        client.setParams((HttpClientParams) params);

        return method;
    }

    public void sendNotification(Notification notification, int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Notification", learningObjectId, instanceId );
        PostMethod method = (PostMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.POST);
        String reportAsXml = serializeNotificationToXML(notification);
        InputStream is = new ByteArrayInputStream(reportAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }


    public LearningObjectInstance getLearningObjectInstance(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        LearningObjectInstance loi = null;
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                loi = deserializeXMLToLearningObjectInstance(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return loi;
    }

    public List<Assessment> getPossibleAssessments(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/PossibleAssessments", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<Assessment> assessments = new ArrayList<Assessment>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                assessments = deserializeXMLToListOfAssessments(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return assessments;
    }

    public List<AssessmentItem> getAssessmentItems(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/AssessmentItems", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<AssessmentItem> assessmentItems = new ArrayList<AssessmentItem>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                assessmentItems = deserializeXMLToListOfAssessmentItems(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return assessmentItems;
    }

    public List<AssessmentStatus> getPossibleAssessmentStatuses(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/PossibleAssessmentStatuses", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<AssessmentStatus> assessmentStatuses = new ArrayList<AssessmentStatus>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                assessmentStatuses = deserializeXMLToListOfPossibleAssessmentStatuses(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return assessmentStatuses;
    }

    public List<AssessmentStatusItem> getAssessmentStatusItems(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/AssessmentStatusItems", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<AssessmentStatusItem> assessmentStatusItems = new ArrayList<AssessmentStatusItem>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                assessmentStatusItems = deserializeXMLToListOfAssessmentStatusItems(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return assessmentStatusItems;
    }

    public List<LearningObjectInstanceUserReport> getLearningObjectInstanceUserReports(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<LearningObjectInstanceUserReport> reports = new ArrayList<LearningObjectInstanceUserReport>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                reports = deserializeXMLToListOfLearningObjectInstanceUserReport(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return reports;
    }

    public LearningObjectInstanceUserReport getLearningObjectInstanceUserReport(int instanceId, int learningObjectId, int userId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports/%s", learningObjectId, instanceId, userId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        LearningObjectInstanceUserReport report = new LearningObjectInstanceUserReport();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    report = deserializeXMLToLearningObjectInstanceUserReport(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return report;
    }

    public void updateLearningObjectInstanceUserReports(List<LearningObjectInstanceUserReport> userReports, int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports", learningObjectId, instanceId);
        PostMethod method = (PostMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.POST);
        String reportsAsXml = serializeLearningObjectInstanceUserReportsToXML(userReports);
        InputStream is = new ByteArrayInputStream(reportsAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }

    public void updateLearningObjectInstanceUserReport(LearningObjectInstanceUserReport userReport, int instanceId, int learningObjectId, int userId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports/%s", learningObjectId, instanceId, userId);
        PutMethod method = (PutMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.PUT);
        String reportAsXml = serializeLearningObjectInstanceUserReportToXML(userReport);
        InputStream is = new ByteArrayInputStream(reportAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }

    public void updateLearningObjectInstance(LearningObjectInstance instance, int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
        PutMethod method = (PutMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.PUT);
        String loiAsXml = serializeLearningObjectInstanceToXML(instance);
        InputStream is = new ByteArrayInputStream(loiAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }

    public List<Organisation> getOrganisationsForCurrentUser() throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/OrganizationsForCurrentUser");
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<Organisation> organizationsForUser = new ArrayList<Organisation>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    organizationsForUser = deserializeXMLToOrganisations(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }


        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return organizationsForUser;
    }

    public List<AppLicense> getAppLicensesForCurrentUser() throws Exception{
        List<AppLicense> appLicenses = new ArrayList<AppLicense>();
        String uri = String.format(_baseUri + "/LearningObjectService.svc/AppLicensesForCurrentUser");
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    appLicenses = deserializeXMLToAppLicenses(method.getResponseBodyAsStream());
                }
            }
        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return appLicenses;
    }
    
    public Site getSiteForCurrentUser() throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/SiteForCurrentUser");
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        Site siteForUser = null;
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    siteForUser = deserializeXMLToSite(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }


        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return siteForUser;
    }
}
