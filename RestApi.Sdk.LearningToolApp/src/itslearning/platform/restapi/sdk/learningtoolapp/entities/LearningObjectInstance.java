package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import java.util.Date;
import itslearning.platform.restApi.sdk.common.entities.Constants;

/**
 * Learning object instance entity for RestApi clients.
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class LearningObjectInstance {
    private int learningObjectInstanceId;
    private int learningObjectId;
    private String title;
    private Date deadLineUTC;
    private Date activeFromUTC;
    private Date activeToUTC;
    private Date modifiedUTC;
    private Date createdUTC;
    private int createdByUserId;
    private boolean isObligatory;
    private Integer assessmentId;
    private Integer assessmentStatusId;
    private boolean isAssessmentVisible;
    private String courseId;
    private String courseCode;
    private String courseSyncKey;
    private int courseOrganisationId;
    private String courseOrganisationSyncKey;
    private Double maxScore;
    private SubmissionType _submissionType;
    private boolean _usePlagiarism;
    private boolean _useAnonymousSubmission;
    private boolean _hasLearningObjectiveAssessmentCriteria;
    
    /**
     * @return the learningObjectInstanceId
     */
    public int getLearningObjectInstanceId()
    {
        return learningObjectInstanceId;
    }

    /**
     * @param learningObjectInstanceId the learningObjectInstanceId to set
     */
    public void setLearningObjectInstanceId(int learningObjectInstanceId)
    {
        this.learningObjectInstanceId = learningObjectInstanceId;
    }

    /**
     * @return the learningObjectId
     */
    public int getLearningObjectId()
    {
        return learningObjectId;
    }

    /**
     * @param learningObjectId the learningObjectId to set
     */
    public void setLearningObjectId(int learningObjectId)
    {
        this.learningObjectId = learningObjectId;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the deadLineUTC
     */
    public Date getDeadLineUTC()
    {
        return deadLineUTC;
    }

    /**
     * @param deadLineUTC the deadLineUTC to set
     */
    public void setDeadLineUTC(Date deadLineUTC)
    {
        this.deadLineUTC = deadLineUTC;
    }

    /**
     * @return the activeFromUTC
     */
    public Date getActiveFromUTC()
    {
        return activeFromUTC;
    }

    /**
     * @param activeFromUTC the activeFromUTC to set
     */
    public void setActiveFromUTC(Date activeFromUTC)
    {
        this.activeFromUTC = activeFromUTC;
    }

    /**
     * @return the activeToUTC
     */
    public Date getActiveToUTC()
    {
        return activeToUTC;
    }

    /**
     * @param activeToUTC the activeToUTC to set
     */
    public void setActiveToUTC(Date activeToUTC)
    {
        this.activeToUTC = activeToUTC;
    }

    /**
     * @return the modifiedUTC
     */
    public Date getModifiedUTC()
    {
        return modifiedUTC;
    }

    /**
     * @param modifiedUTC the modifiedUTC to set
     */
    public void setModifiedUTC(Date modifiedUTC)
    {
        this.modifiedUTC = modifiedUTC;
    }

    /**
     * @return the createdUTC
     */
    public Date getCreatedUTC()
    {
        return createdUTC;
    }

    /**
     * @param createdUTC the createdUTC to set
     */
    public void setCreatedUTC(Date createdUTC)
    {
        this.createdUTC = createdUTC;
    }

    /**
     * @return the createdByUserId
     */
    public int getCreatedByUserId()
    {
        return createdByUserId;
    }

    /**
     * @param createdByUserId the createdByUserId to set
     */
    public void setCreatedByUserId(int createdByUserId)
    {
        this.createdByUserId = createdByUserId;
    }

    /**
     * @return the isObligatory
     */
    public boolean isIsObligatory()
    {
        return isObligatory;
    }

    /**
     * @param isObligatory the isObligatory to set
     */
    public void setIsObligatory(boolean isObligatory)
    {
        this.isObligatory = isObligatory;
    }

    /**
     * @return the assessmentId
     */
    public Integer getAssessmentId()
    {
        return assessmentId;
    }

    /**
     * @param assessmentId the assessmentId to set
     */
    public void setAssessmentId(Integer assessmentId)
    {
        this.assessmentId = assessmentId;
    }

    /**
     * @return the assessmentStatusId
     */
    public Integer getAssessmentStatusId()
    {
        return assessmentStatusId;
    }

    /**
     * @param assessmentStatusId the assessmentStatusId to set
     */
    public void setAssessmentStatusId(Integer assessmentStatusId)
    {
        this.assessmentStatusId = assessmentStatusId;
    }
    
    /**
     * @return True if learning object instance assessment is visible for students.
     */
    public boolean getIsAssessmentVisible()
    {
        return isAssessmentVisible;
    }

    /**
     * @param isAssessmentVisible True if learning object instance assessment is visible for students.
     */
    public void setIsAssessmentVisible(boolean isAssessmentVisible)
    {
        this.isAssessmentVisible = isAssessmentVisible;
    }
    
    /**
     * Is set only for apps permitted to get protected data.
     * @return the courseId (format is [CustomerId]-[CourseId]).
     */
    public String getCourseId()
    {
        return courseId;
    }

    /**
     * @param courseId the courseId to set (format is [CustomerId]-[CourseId]).
     */
    public void setCourseId(String courseId)
    {
        this.courseId = courseId;
    }

    /**
     * Is set only for apps permitted to get protected data.
     * @return the courseSyncKey (format is [CustomerId]-[CourseSyncKey]).
     */
    public String getCourseSyncKey()
    {
        return courseSyncKey;
    }

    /**
     * @param courseSyncKey the courseSyncKey to set (format is [CustomerId]-[CourseSyncKey]).
     */
    public void setCourseSyncKey(String courseSyncKey)
    {
        this.courseSyncKey = courseSyncKey;
    }

    /**
     * Is set only for apps permitted to get protected data.
     * @return the courseCode
     */
    public String getCourseCode()
    {
        return courseCode;
    }

    /**
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
    }
    
    /**
     * Is set only for apps permitted to get protected data.
     * @return the courseOrganisationId
     */
    public int getCourseOrganisationId()
    {
        return courseOrganisationId;
    }

    /**
     * @param courseOrganisationId the courseOrganisationId to set
     */
    public void setCourseOrganisationId(int courseOrganisationId)
    {
        this.courseOrganisationId = courseOrganisationId;
    }
    
    /**
     * Is set only for apps permitted to get protected data.
     * @return the courseOrganisationSyncKey
     */
    public String getCourseOrganisationSyncKey()
    {
        return courseOrganisationSyncKey;
    }

    /**
     * @param courseOrganisationSyncKey the courseOrganisationSyncKey to set
     */
    public void setCourseOrganisationSyncKey(String courseOrganisationSyncKey)
    {
        this.courseOrganisationSyncKey = courseOrganisationSyncKey;
    }
    
    /**
     * MaxScore for element
     * @return the maxScore
     */
    public Double getMaxScore()
    {
        return maxScore;
    }

    /**
     * @param maxScore the maxScore to set
     */
    public void setMaxScore(Double maxScore)
    {
        this.maxScore = maxScore;
    }
    
    /**
     * Determine when LearningObjectInstance uses Score scale
     */
    public Boolean getUseScore()
    {
        return getAssessmentId() != null ? getAssessmentId() == Constants.ScoreAssessmentId : null;
    }
    
    /**
     * @return Defines whether participants work on the element individually or in group.
     */
    public SubmissionType getSubmissionType()
    {
        return _submissionType;
    }

    /**
     * @param submissionType Defines whether participants work on the element individually or in group.
     */
    public void setSubmissionType(SubmissionType submissionType)
    {
        _submissionType = submissionType;
    }
    
    /**
     * @return Defines whether plagiarism is enabled for learning object instance.
     */
    public boolean getUsePlagiarism()
    {
        return _usePlagiarism;
    }

    /**
     * @param usePlagiarism Defines whether plagiarism is enabled for learning object instance.
     */
    public void setUsePlagiarism(boolean usePlagiarism)
    {
        _usePlagiarism = usePlagiarism;
    }
    
    /**
     * @return Indicates that teachers should not see students names.
     */
    public boolean getUseAnonymousSubmission()
    {
        return _useAnonymousSubmission;
    }

    /**
     * @param useAnonymousSubmission Indicates that teachers should not see students names.
     */
    public void setUseAnonymousSubmission(boolean useAnonymousSubmission)
    {
        _useAnonymousSubmission = useAnonymousSubmission;
    }
    
    /**
     * @return hasLearningObjectiveAssessmentCriteria Indicates that this learning object instance has at least one learning objective assessment criterion attached to it. It is an optimization, that allows client code to safely skip REST API calls for assessment criteria.
     */
    public boolean getHasLearningObjectiveAssessmentCriteria()
    {
        return _hasLearningObjectiveAssessmentCriteria;
    }

    /**
     * @param hasLearningObjectiveAssessmentCriteria Indicates that this learning object instance has at least one learning objective assessment criterion attached to it.
     */
    public void setHasLearningObjectiveAssessmentCriteria(boolean hasLearningObjectiveAssessmentCriteria)
    {
        _hasLearningObjectiveAssessmentCriteria = hasLearningObjectiveAssessmentCriteria;
    }
}
