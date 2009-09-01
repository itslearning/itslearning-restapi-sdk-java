/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpParams;

/**
 *
 * @author Amund Trovåg
 */
public class LearningObjectServiceClientRest
{

    public void getLearningObjectInstance(ApiSession apiSession, int instanceId, int learningObjectId)
    {

        // Define our Restlet HTTP client.
        //Client client = new Client(Protocol.HTTP);
        String uri = String.format("http://betarest.itslearning.com/Restapi/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
        Calendar now = GregorianCalendar.getInstance();
        apiSession.setLastRequestDateTimeUtc(new Date(now.getTimeInMillis()));
        apiSession.setHash(CryptographyHelper.computeHash(apiSession, "c14ba64d-5a6d-499f-836e-52a07c41d3dc"));

        String authHeader = AuthorizationHelper.toAuthorizationHeader(apiSession);

        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        HttpMethod method = new GetMethod(uri);
        HttpParams params = DefaultHttpParams.getDefaultParams();
        //params.setParameter("Authorization", authHeader);
        method.setRequestHeader("Authorization", authHeader);
        httpClient.setParams((HttpClientParams) params);
        try
        {
            int statusCode = httpClient.executeMethod(method);
            byte[] responseBody = method.getResponseBody();


        } catch (IOException ex)
        {
            Logger.getLogger(LearningObjectServiceClientRest.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
