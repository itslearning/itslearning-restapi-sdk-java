/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import javax.xml.ws.BindingProvider;
import org.tempuri.*;
/**
 * LearningObjectService client wrapper.
 * @author Amund Trovåg
 */
class LearningObjectServiceClientProxy implements ILearningObjectService
{
    public GetLearningObjectInstanceResponse getLearningObjectInstance(GetLearningObjectInstance parameters)
    {
        try
        { // Call Web Service Operation
            org.tempuri.LearningObjectService service = new org.tempuri.LearningObjectService();
            org.tempuri.ILearningObjectService port = service.getWebHttpBindingILearningObjectService();
            
            BindingProvider bp = (BindingProvider) port;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://restapi.itslearning.com/");

            // TODO process result here
            org.tempuri.GetLearningObjectInstanceResponse result = port.getLearningObjectInstance(parameters);
            System.out.println("Result = "+result);
            return result;
        }
        catch (Exception ex)
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
        throw new UnsupportedOperationException("Not supported yet.");
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
}
