/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.jws.WebMethod;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import org.tempuri.*;

/**
 * LearningObjectService client wrapper.
 * @author Amund Trovåg
 */
class LearningObjectServiceClientProxy implements ILearningObjectService
{
    private org.tempuri.LearningObjectService _service = new org.tempuri.LearningObjectService();
    private String _endPointAddress = "";
    private ApiSession _apiSession;

    public LearningObjectServiceClientProxy(){

    }
    
    public GetLearningObjectInstanceResponse getLearningObjectInstance(GetLearningObjectInstance parameters)
    {
        try
        { // Call Web Service Operation
            org.tempuri.ILearningObjectService port = _service.getWebHttpBindingILearningObjectService();

            BindingProvider bp = (BindingProvider) port;
            
            // Set correct endpoint address.  This is imporant since the service will be available in different
            // environments.
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _endPointAddress);
            bp.getRequestContext().put(MessageContext.HTTP_REQUEST_METHOD, "GET");


            // TODO: refactor this, and do it in a method or something.
            // TODO also since we need to compute hash for each call, we need access to sharedsecret, so fix this
            Calendar now = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
            _apiSession.setLastRequestDateTimeUtc(new Date(now.getTimeInMillis()));
            _apiSession.setHash(CryptographyHelper.computeHash(_apiSession, "c14ba64d-5a6d-499f-836e-52a07c41d3dc"));


            String authHeader = AuthorizationHelper.toAuthorizationHeader(_apiSession);

            bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS,
                                    Collections.singletonMap("Authorization",Collections.singletonList(authHeader)));

            bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS,
                                    Collections.singletonMap("Content-Type",Collections.singletonList("text/xml")));

            bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS,
                                    Collections.singletonMap("Accept",Collections.singletonList("text/xml")));
            // TODO process result here
            org.tempuri.GetLearningObjectInstanceResponse result = port.getLearningObjectInstance(parameters);
            //System.out.println("Result = " + result);
            return result;
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }

    }

    public UpdateLearningObjectInstanceResponse updateLearningObjectInstance(UpdateLearningObjectInstance parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GetPossibleAssessmentsResponse getPossibleAssessments(GetPossibleAssessments parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GetAssessmentItemsResponse getAssessmentItems(GetAssessmentItems parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GetPossibleAssessmentStatusesResponse getPossibleAssessmentStatuses(GetPossibleAssessmentStatuses parameters)
    {
        try
        { // Call Web Service Operation
            org.tempuri.ILearningObjectService port = _service.getWebHttpBindingILearningObjectService();

            BindingProvider bp = (BindingProvider) port;
            // Set correct endpoint address.  This is imporant since the service will be available in different
            // environments.
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _endPointAddress);

            // TODO: refactor this, and do it in a method or something.
            // TODO also since we need to compute hash for each call, we need access to sharedsecret, so fix this
            Calendar now = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
            _apiSession.setLastRequestDateTimeUtc(new Date(now.getTimeInMillis()));
            _apiSession.setHash(CryptographyHelper.computeHash(_apiSession, "c14ba64d-5a6d-499f-836e-52a07c41d3dc"));
            

            String authHeader = AuthorizationHelper.toAuthorizationHeader(_apiSession);

            bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS,
                                    Collections.singletonMap("Authorization",Collections.singletonList(authHeader)));
            Object o = bp.getRequestContext().get(MessageContext.HTTP_REQUEST_HEADERS);
            // TODO initialize WS operation arguments here
            // TODO process result here
            org.tempuri.GetPossibleAssessmentStatusesResponse result = port.getPossibleAssessmentStatuses(parameters);
            System.out.println("Result = "+result);
            return result;
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public GetAssessmentStatusItemsResponse getAssessmentStatusItems(GetAssessmentStatusItems parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public UpdateLearningObjectInstanceUserReportResponse updateLearningObjectInstanceUserReport(UpdateLearningObjectInstanceUserReport parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public UpdateLearningObjectInstanceUserReportsResponse updateLearningObjectInstanceUserReports(UpdateLearningObjectInstanceUserReports parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GetLearningObjectInstanceUserReportResponse getLearningObjectInstanceUserReport(GetLearningObjectInstanceUserReport parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GetLearningObjectInstanceUserReportsResponse getLearningObjectInstanceUserReports(GetLearningObjectInstanceUserReports parameters)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the _endPointAddress
     */
    public String getEndPointAddress()
    {
        return _endPointAddress;
    }

    /**
     * @param endPointAddress the _endPointAddress to set
     */
    public void setEndPointAddress(String endPointAddress)
    {
        this._endPointAddress = endPointAddress;
    }

    /**
     * @return the _apiSession
     */
    public ApiSession getApiSession()
    {
        return _apiSession;
    }

    /**
     * @param apiSession the _apiSession to set
     */
    public void setApiSession(ApiSession apiSession)
    {
        this._apiSession = apiSession;
    }
}
