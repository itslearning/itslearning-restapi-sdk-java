
package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restapi.sdk.learningtoolapp.entities.Assessment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatus;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatusItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUserReport;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Notification;
import java.util.List;

/**
 * Defines itslearning's restAPI for applications
 * @author Amund
 */
public interface ILearningObjectServiceRestClient {

    /**
     * updates a list of userreports on a learningobjectinstance
     * @param userReports
     * @param instanceId
     * @param learningObjectId
     * @throws Exceptioin
     */
    public void updateLearningObjectInstanceUserReports(List<LearningObjectInstanceUserReport> userReports, int instanceId, int learningObjectId) throws Exception;
    /**
     * updates a single userreport for a student on a learningobjectinstance
     * @param userReport
     * @param instanceId
     * @param learningObjectId
     * @param userId
     * @throws java.lang.Exception
     */
    public void updateLearningObjectInstanceUserReport(LearningObjectInstanceUserReport userReport, int instanceId, int learningObjectId, int userId) throws Exception;
    /**
     * update instance of learning object instance
     * @param instance
     * @param instanceId
     * @param learningObjectId
     * @throws java.lang.Exception
     */
    public void updateLearningObjectInstance(LearningObjectInstance instance, int instanceId, int learningObjectId) throws Exception;
    /**
     * get instance of learning object instance
     * @param instanceId
     * @param learningObjectId
     * @return
     * @throws java.lang.Exception
     */
    public LearningObjectInstance getLearningObjectInstance(int instanceId, int learningObjectId) throws Exception;
    /**
     * get possible assessments for learning object instance
     * @param instanceId
     * @param learningObjectId
     * @return
     * @throws java.lang.Exception
     */
    public List<Assessment> getPossibleAssessments(int instanceId, int learningObjectId) throws Exception;
    /**
     * get assessment items that can be used for evaluating on an learning object instance
     * @param instanceId
     * @param learningObjectId
     * @return
     * @throws java.lang.Exception
     */
    public List<AssessmentItem> getAssessmentItems(int instanceId, int learningObjectId) throws Exception;
    /**
     * get possible assessment statuses for a learning object instance
     * @param instanceId
     * @param learningObjectId
     * @return
     * @throws java.lang.Exception
     */
    public List<AssessmentStatus> getPossibleAssessmentStatuses(int instanceId, int learningObjectId) throws Exception;
    /**
     * get assessment status items that can be used for setting status on learning object instance
     * @param instanceId
     * @param learningObjectId
     * @return
     * @throws java.lang.Exception
     */
    public List<AssessmentStatusItem> getAssessmentStatusItems(int instanceId, int learningObjectId) throws Exception;
    /**
     * get reports (assessment etc) for users with access to learning object instance
     * @param instanceId
     * @param learningObjectId
     * @return
     * @throws java.lang.Exception
     */
    public List<LearningObjectInstanceUserReport> getLearningObjectInstanceUserReports(int instanceId, int learningObjectId) throws Exception;
    /**
     * get report (assessment etc) for user with access to learning object instance
     * @param instanceId
     * @param learningObjectId
     * @param userId
     * @return
     * @throws java.lang.Exception
     */
    public LearningObjectInstanceUserReport getLearningObjectInstanceUserReport(int instanceId, int learningObjectId, int userId) throws Exception;

    /**
     * Send a notification
     * 
     * @param notification
     * @param instanceId
     * @param learningObjectId
     * @throws java.lang.Exception
     */
    public void sendNotification(Notification notification, int instanceId, int learningObjectId ) throws Exception;
}
