/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;

/**
 *
 * @author Amund
 */
public interface ILearningObjectServiceRestClient {
/*
 * [GET] 	/learningObjects/{learningObjectId}/instances/{instanceId} 	GetLearningObjectInstance 	get instance of learning object instance
[PUT] 	/learningObjects/{learningObjectId}/instances/{instanceId} 	UpdateLearningObjectInstance 	update instance of learning object instance
[GET] 	/learningObjects/{learningObjectId}/instances/{instanceId}/PossibleAssessments 	GetPossibleAssessments 	get possible assessments for learning object instance
[GET] 	/learningObjects/{learningObjectId}/instances/{instanceId}/AssessmentItems 	GetAssessmentItems 	get assessment items that can be used for evaluating on an learning object instance
[GET] 	/learningObjects/{learningObjectId}/instances/{instanceId}/PossibleAssessmentStatuses 	GetPossibleAssessmentStatuses 	get possible assessment statuses for a learning object instance
[GET] 	/learningObjects/{learningObjectId}/instances/{instanceId}/AssessmentStatusItems 	GetAssessmentStatusItems 	get assessment status items that can be used for setting status on learning object instance
[GET] 	/learningObjects/{learningObjectId}/instances/{instanceId}/Reports 	GetLearningObjectInstanceUserReports 	get reports (assessment etc) for users with access to learning object instance
[POST] 	/learningObjects/{learningObjectId}/instances/{instanceId}/Reports 	UpdateLearningObjectInstanceUserReports 	update reports (assessment etc) for users with access to learning object instance
[GET] 	/learningObjects/{learningObjectId}/instances/{instanceId}/Reports/{userId} 	GetLearningObjectInstanceUserReport 	get report (assessment etc) for user with access to learning object instance
[PUT] 	/learningObjects/{learningObjectId}/instances/{instanceId}/Reports/{userId}
 * */
    public LearningObjectInstance getLearningObjectInstance(int instanceId, int learningObjectId) throws Exception;
}
