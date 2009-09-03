/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;
import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpParams;

/**
 *
 * @author Amund Trovåg
 */
public class LearningObjectServiceClientRest implements ILearningObjectServiceRestClient
{

    private ApiSession _apiSession;

    public LearningObjectServiceClientRest(ApiSession apiSession)
    {
        this._apiSession = apiSession;
    }

    public LearningObjectInstance getLearningObjectInstance(int instanceId, int learningObjectId)
    {

        // Define our Restlet HTTP client.
        //Client client = new Client(Protocol.HTTP);
        String uri = String.format("http://betarest.itslearning.com/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
        Calendar now = GregorianCalendar.getInstance();
        _apiSession.setLastRequestDateTimeUtc(new Date(now.getTimeInMillis()));
        _apiSession.setHash(CryptographyHelper.computeHash(_apiSession, "c14ba64d-5a6d-499f-836e-52a07c41d3dc"));

        String authHeader = AuthorizationHelper.toAuthorizationHeader(_apiSession);

        HttpClient httpClient = new HttpClient();
        HttpMethod method = new GetMethod(uri);
        HttpParams params = DefaultHttpParams.getDefaultParams();
        method.setRequestHeader("Authorization", authHeader);
        httpClient.setParams((HttpClientParams) params);
        try
        {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                // TODO
                throw new HttpException("TODO");
            }

            String[] dateFormats = new String[]
            {
                "yyyyMMdd", "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd"
            };
            DateConverter converter = new DateConverter("yyyy-MM-dd'T'HH:mm:ss.S'Z'", dateFormats);

            String responseBody = new String(method.getResponseBodyAsString().getBytes(Charset.forName("UTF-8")));
            XStream xStream = new XStream();
            xStream.alias("LearningObjectInstance", LearningObjectInstance.class);
            xStream.registerConverter(converter);
            xStream.registerConverter(new NullConverter());
            xStream.aliasField("ActiveFromUtc", LearningObjectInstance.class, "activeFromUTC");
            xStream.aliasField("ActiveToUtc", LearningObjectInstance.class, "activeToUTC");
            xStream.aliasField("LearningObjectInstanceId", LearningObjectInstance.class, "learningObjectInstanceId");
            xStream.aliasField("LearningObjectId", LearningObjectInstance.class, "learningObjectId");
            xStream.aliasField("Title", LearningObjectInstance.class, "title");
            xStream.aliasField("DeadlineUtc", LearningObjectInstance.class, "deadLineUTC");
            xStream.aliasField("ModifiedUtc", LearningObjectInstance.class, "modifiedUTC");
            xStream.aliasField("CreatedUtc", LearningObjectInstance.class, "createdUTC");
            xStream.aliasField("CreatedByUserId", LearningObjectInstance.class, "createdByUserId");
            xStream.aliasField("IsObligatory", LearningObjectInstance.class, "isObligatory");
            xStream.aliasField("AssessmentId", LearningObjectInstance.class, "assessmentId");
            xStream.aliasField("AssessmentStatusId", LearningObjectInstance.class, "assessmentStatusId");

            Object instance = xStream.fromXML(method.getResponseBodyAsStream());
            System.out.println(instance);


        } catch (IOException ex)
        {
            Logger.getLogger(LearningObjectServiceClientRest.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            method.releaseConnection();
        }
        return null;
    }
    
}
