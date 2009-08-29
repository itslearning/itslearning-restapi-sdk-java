/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.entities.ApiSession;
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

    public GetLearningObjectInstanceResponse getLearningObjectInstance(GetLearningObjectInstance parameters)
    {
        try
        { // Call Web Service Operation
            org.tempuri.ILearningObjectService port = _service.getWebHttpBindingILearningObjectService();

            BindingProvider bp = (BindingProvider) port;
            // Set correct endpoint address.  This is imporant since the service will be available in different
            // environments.
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, _endPointAddress);
            String authHeader = AuthorizationHelper.toAuthorizationHeader(_apiSession);
            bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, authHeader);
            // TODO process result here
            org.tempuri.GetLearningObjectInstanceResponse result = port.getLearningObjectInstance(parameters);
            System.out.println("Result = " + result);
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
            String authHeader = AuthorizationHelper.toAuthorizationHeader(_apiSession);
            bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, authHeader);
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
