package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restapi.sdk.learningtoolapp.entities.AppLicense;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Organisation;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Assessment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatus;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatusItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.EntityConstants.OrderDirection;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUser;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUserReport;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUserReportCommentOnComment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Notification;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Site;
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
     * @throws Exception
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

    /*
     * Updates comment log on report (assessment etc.) for user with access to learning object instance. 
     * @param reportComment Comment log entry.
     * @param instanceId Learning object instance Id.
     * @param learningObjectId Learning object Id.
     * @param userId Id of the user whose report will be updated.
     * @throws Exception
    */
    public void updateLearningObjectInstanceUserReportComment(LearningObjectInstanceUserReportCommentOnComment reportComment, int instanceId, int learningObjectId, int userId) throws Exception;
    
    /*
     * Updates comment log on report (assessment etc.) for all participants on the collaboration.
     * @param reportComment Comment log entry.
     * @param instanceId Learning object instance Id.
     * @param learningObjectId Learning object Id.
     * @param collaborationId Id of the collaboration (users group) whose reports will be updated.
     * @throws Exception
    */
    public void updateLearningObjectInstanceUserReportCommentForCollaboration(LearningObjectInstanceUserReportCommentOnComment reportComment, int instanceId, int learningObjectId, int collaborationId) throws Exception;
    
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
     * get reports (assessment etc) for users with access to learning object instance, limited by paging
     * @param instanceId
     * @param learningObjectId
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws java.lang.Exception
     */
    public List<LearningObjectInstanceUserReport> getLearningObjectInstanceUserReports(int instanceId, int learningObjectId, int pageIndex, int pageSize) throws Exception;
    /**
     * get reports (assessment etc) for users with access to learning object instance, limited by paging and ordered by defined field
     * @param instanceId
     * @param learningObjectId
     * @param pageIndex
     * @param pageSize
     * @param orderBy
     * @return
     * @throws java.lang.Exception
     */
    public List<LearningObjectInstanceUserReport> getLearningObjectInstanceUserReports(int instanceId, int learningObjectId, int pageIndex, int pageSize, LearningObjectInstanceUserReport.OrderBy orderBy) throws Exception;
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
     * get total number of reports (assessment etc) for users with access to learning object instance
     * @param instanceId
     * @param learningObjectId
     * @return
     * @throws java.lang.Exception
     */
    public int getLearningObjectInstanceUserReportsCount(int instanceId, int learningObjectId) throws Exception;
    /**
     * get users with access to learning object instance
     * @param instanceId
     * @param learningObjectId
     * @return
     * @throws java.lang.Exception
     */
    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId) throws Exception;
    /**
     * get users with access to learning object instance, limited by paging
     * @param instanceId
     * @param learningObjectId
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws java.lang.Exception
     */
    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId, int pageIndex, int pageSize) throws Exception;
    /**
     * get users with access to learning object instance, limited by paging and ordered by defined field and order direction (i.e. asc or desc)
     * @param instanceId
     * @param learningObjectId
     * @param pageIndex
     * @param pageSize
     * @param orderBy
     * @param orderDirection
     * @return
     * @throws java.lang.Exception
     */
    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId, int pageIndex, int pageSize, LearningObjectInstanceUser.OrderBy orderBy, OrderDirection orderDirection) throws Exception;
    /**
     * get users with access to learning object instance, limited by paging and ordered by defined field and order direction (i.e. asc or desc)
     * @param instanceId
     * @param learningObjectId
     * @param userIds The array of user IDs to filter by.
     * @param includeTeachers Defines whether teachers will be included as well (by default only students are included).
     * @param pageIndex
     * @param pageSize
     * @param orderBy
     * @param orderDirection
     * @return
     * @throws java.lang.Exception
     */
    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId, int[] userIds, boolean includeTeachers, int pageIndex, int pageSize, LearningObjectInstanceUser.OrderBy orderBy, OrderDirection orderDirection) throws Exception;
    
    /**
     * get total number of users with access to learning object instance
     * @param instanceId
     * @param learningObjectId
     * @param userIds The array of user IDs to filter by.
     * @param includeTeachers Defines whether teachers will be included as well (by default only students are included).
     * @return
     * @throws java.lang.Exception
     */
    public int getLearningObjectInstanceUsersCount(int instanceId, int learningObjectId, int[] userIds, boolean includeTeachers) throws Exception;
    
    /**
     * Send a notification
     * 
     * @param notification
     * @param instanceId
     * @param learningObjectId
     * @throws java.lang.Exception
     */
    public void sendNotification(Notification notification, int instanceId, int learningObjectId ) throws Exception;

    /**
     * Send a notification to specific list of users
     * 
     * @param notification
     * @param instanceId
     * @param learningObjectId
     * @param usersIds
     * @param senderUserId 
     * @throws java.lang.Exception
     */
    public void sendNotificationToUsers(Notification notification, int learningObjectId, int instanceId, int[] receiverUserIds, int senderUserId) throws Exception;

        /**
     * Notifies itslearning that that the learning object instance has been updated/changed.
     * @param instanceId
     * @param learningObjectId
     * @throws Exception 
     */
    public void setUpdated(int instanceId, int learningObjectId ) throws Exception;

    /**
     * Gets organisatons for the currently logged on user
     */
    public List<Organisation> getOrganisationsForCurrentUser() throws Exception;

    /**
     * Gets appLicenses for the currently logged on user
     * @return
     * @throws Exception
     */
    public List<AppLicense> getAppLicensesForCurrentUser() throws Exception;
    
    /**
     * Get information about site from itslearning for the currently logged on user
     */
    public Site getSiteForCurrentUser() throws Exception;
    
    /**
     * Gets the organisations for learning object
     * /
     * @param learningObjectId
     * @param instanceId
     * @return 
     * @throws Exception
     */
    public List<Organisation> getOrganisationsForLearningObjectInstance(int learningObjectId, int instanceId) throws Exception;
}
