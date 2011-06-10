package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import java.util.Date;

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
    private String courseId;
    private String courseCode;
    private String courseSyncKey;
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
}
