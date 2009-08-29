/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import org.tempuri.*;

/**
 * Wrapper for LearningObjectService. Use this class to call the rest api.
 * @author Amund Trovåg
 */
public class LearningObjectServiceClient
{

    private LearningObjectServiceClientProxy _proxy = new LearningObjectServiceClientProxy();

    public LearningObjectServiceClient(String endPointAddress, ApiSession session)
    {
        _proxy.setEndPointAddress(endPointAddress);
        _proxy.setApiSession(session);
    }

    public GetLearningObjectInstanceResponse getLearningObjectInstance(int instanceId, int learningObjectId)
    {

        GetLearningObjectInstance params = new GetLearningObjectInstance();
        params.setInstanceId("" + instanceId);
        params.setLearningObjectId("" + learningObjectId);
        return _proxy.getLearningObjectInstance(params);

    }

    public GetPossibleAssessmentStatusesResponse getPossibleAssessmentStatuses(int instanceId, int learningObjectId)
    {
        GetPossibleAssessmentStatuses params = new GetPossibleAssessmentStatuses();
        params.setInstanceId("" + instanceId);
        params.setLearningObjectId("" + learningObjectId);
        return _proxy.getPossibleAssessmentStatuses(params);
    }

    // TODO add methods
    /**
     * @return the _endPointAddress
     */
    public String getEndPointAddress()
    {
        return _proxy.getEndPointAddress();
    }

    /**
     * @param endPointAddress the _endPointAddress to set
     */
    public void setEndPointAddress(String endPointAddress)
    {
        _proxy.setEndPointAddress(endPointAddress);
    }

    /**
     * @return the _session
     */
    public ApiSession getApiSession()
    {
        return _proxy.getApiSession();
    }

    /**
     * @param session the _session to set
     */
    public void setApiSession(ApiSession session)
    {
        _proxy.setApiSession(session);
    }
}
