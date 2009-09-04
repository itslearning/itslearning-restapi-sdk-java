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
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
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

    protected enum HttpMethodType
    {

        GET, PUT, POST, DELETE;
    }

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


    public LearningObjectInstance getLearningObjectInstance(int instanceId, int learningObjectId)
    {

        String uri = String.format("/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
        HttpClient httpClient = new HttpClient();
        HttpMethod method = getInitializedHttpMethod(httpClient, uri, HttpMethodType.GET);

        try
        {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                // TODO, throw ItslException
                throw new HttpException("TODO");
            }

            
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        } finally
        {
            method.releaseConnection();
        }
        return null;
    }
}
