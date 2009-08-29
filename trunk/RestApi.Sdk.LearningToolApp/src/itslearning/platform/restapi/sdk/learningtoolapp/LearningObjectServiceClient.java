/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp;
import org.tempuri.*;


/**
 *
 * @author Amund Trovåg
 */
public class LearningObjectServiceClient {
    
    public GetLearningObjectInstanceResponse getLearningObjectInstance(int instanceId, int learningObjectId){
        GetLearningObjectInstance params = new GetLearningObjectInstance();
        params.setInstanceId(""+instanceId);
        params.setLearningObjectId(""+learningObjectId);
        return new LearningObjectServiceClientProxy().getLearningObjectInstance(params);
    }
}
