/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.ExceptionHandler;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Assessment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatus;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatusItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
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
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Amund Trovåg
 */
public class LearningObjectServicetRestClient implements ILearningObjectServiceRestClient
{

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


    static
    {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    private ApiSession _apiSession;
    private String _baseUri;

    /**
     *
     * @param apiSession
     * @param baseUri the baseUri of the restApi, e.g. http://betarest.itslearning.com depending on the environment. This must be set by client
     * in order to communicate.
     */
    public LearningObjectServicetRestClient(ApiSession apiSession, String baseUri)
    {
        if(apiSession==null || baseUri==null || baseUri.isEmpty())
        {
            throw new IllegalArgumentException("The client needs an apisession and a baseUri to work");
        }
        this._apiSession = apiSession;
        this._baseUri = baseUri;
    }

    public List<AssessmentStatus> getPossibleAssessmentStatuses(int instanceId, int learningObjectId) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<AssessmentStatusItem> getAssessmentStatusItems(int instanceId, int learningObjectId) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected enum HttpMethodType
    {

        GET, PUT, POST, DELETE;
    }

    private List<AssessmentItem> deserializeXMLToListOfAssessmentItems(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<AssessmentItem> result = new ArrayList<AssessmentItem>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessmentItem";
        // TODO untested, no data from service - need to check this
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
        _apiSession.setHash(CryptographyHelper.computeHash(_apiSession, "c14ba64d-5a6d-499f-836e-52a07c41d3dc"));

        String authHeader = AuthorizationHelper.toAuthorizationHeader(_apiSession);

        HttpMethod method;
        switch (methodType)
        {
            case GET:
                method = new GetMethod(uri);
                break;
            case PUT:
                method = new PutMethod(uri);
                break;
            case POST:
                method = new PostMethod(uri);
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
        HttpClient httpClient = new HttpClient();
        HttpMethod method = getInitializedHttpMethod(httpClient, uri, HttpMethodType.GET);
        LearningObjectInstance loi = null;
        try
        {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            } else
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
        HttpClient httpClient = new HttpClient();
        HttpMethod method = getInitializedHttpMethod(httpClient, uri, HttpMethodType.GET);
        List<Assessment> assessments = new ArrayList<Assessment>();
        try
        {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            } else
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
        HttpClient httpClient = new HttpClient();
        HttpMethod method = getInitializedHttpMethod(httpClient, uri, HttpMethodType.GET);
        List<AssessmentItem> assessmentItems = new ArrayList<AssessmentItem>();
        try
        {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            } else
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
}
