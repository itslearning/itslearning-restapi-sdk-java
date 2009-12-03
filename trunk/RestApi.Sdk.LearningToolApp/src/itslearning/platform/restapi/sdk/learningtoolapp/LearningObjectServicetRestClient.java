package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.ExceptionHandler;
import itslearning.platform.restApi.sdk.common.ThreadSafeDateFormat;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restApi.sdk.common.entities.Constants.SimpleStatusType;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Assessment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatus;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatusItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.EntityConstants;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUserReport;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

        root.addElement("UserId").addText("" + userReport.getUserId());

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
        if (instance.getModifiedUTC() != null)
        {
            String modifiedUTCText = sdf.format(instance.getModifiedUTC());
            root.addElement("ModifiedUtc").addText(modifiedUTCText);
        }
        if (instance.getDeadLineUTC() != null)
        {
            String deadlineUTCText = sdf.format(instance.getDeadLineUTC());
            root.addElement("DeadlineUtc").addText(deadlineUTCText);
        }

        root.addElement("LearningObjectId").addText("" + instance.getLearningObjectId());
        root.addElement("LearningObjectInstanceId").addText("" + instance.getLearningObjectInstanceId());
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

    public LearningObjectInstance getLearningObjectInstance(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s/PossibleAssessments", learningObjectId, instanceId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s/AssessmentItems", learningObjectId, instanceId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s/PossibleAssessmentStatuses", learningObjectId, instanceId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s/AssessmentStatusItems", learningObjectId, instanceId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports", learningObjectId, instanceId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports/%s", learningObjectId, instanceId, userId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports", learningObjectId, instanceId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports/%s", learningObjectId, instanceId, userId);
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
        String uri = String.format(_baseUri + "/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
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
}
