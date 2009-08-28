/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import java.util.Date;

/**
 * Learning object instance entity for RestApi clients.
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class LearningObjectInstance {
    private int _learningObjectInstanceId;
    private int _learningObjectId;
    private String _title;
    private Date _deadLineUTC;
    private Date _activeFromUTC;
    private Date _activeToUTC;
    private Date _modifiedUTC;
    private Date _createdUTC;
    private int _createdByUserId;
    private boolean _isObligatory;
    private Integer _assessmentId;
    private Integer _assessmentStatusId;

    /**
     * @return the _learningObjectInstanceId
     */
    public int getLearningObjectInstanceId()
    {
        return _learningObjectInstanceId;
    }

    /**
     * @param learningObjectInstanceId the _learningObjectInstanceId to set
     */
    public void setLearningObjectInstanceId(int learningObjectInstanceId)
    {
        this._learningObjectInstanceId = learningObjectInstanceId;
    }

    /**
     * @return the _learningObjectId
     */
    public int getLearningObjectId()
    {
        return _learningObjectId;
    }

    /**
     * @param learningObjectId the _learningObjectId to set
     */
    public void setLearningObjectId(int learningObjectId)
    {
        this._learningObjectId = learningObjectId;
    }

    /**
     * @return the _title
     */
    public String getTitle()
    {
        return _title;
    }

    /**
     * @param title the _title to set
     */
    public void setTitle(String title)
    {
        this._title = title;
    }

    /**
     * @return the _deadLineUTC
     */
    public Date getDeadLineUTC()
    {
        return _deadLineUTC;
    }

    /**
     * @param deadLineUTC the _deadLineUTC to set
     */
    public void setDeadLineUTC(Date deadLineUTC)
    {
        this._deadLineUTC = deadLineUTC;
    }

    /**
     * @return the _activeFromUTC
     */
    public Date getActiveFromUTC()
    {
        return _activeFromUTC;
    }

    /**
     * @param activeFromUTC the _activeFromUTC to set
     */
    public void setActiveFromUTC(Date activeFromUTC)
    {
        this._activeFromUTC = activeFromUTC;
    }

    /**
     * @return the _activeToUTC
     */
    public Date getActiveToUTC()
    {
        return _activeToUTC;
    }

    /**
     * @param activeToUTC the _activeToUTC to set
     */
    public void setActiveToUTC(Date activeToUTC)
    {
        this._activeToUTC = activeToUTC;
    }

    /**
     * @return the _modifiedUTC
     */
    public Date getModifiedUTC()
    {
        return _modifiedUTC;
    }

    /**
     * @param modifiedUTC the _modifiedUTC to set
     */
    public void setModifiedUTC(Date modifiedUTC)
    {
        this._modifiedUTC = modifiedUTC;
    }

    /**
     * @return the _createdUTC
     */
    public Date getCreatedUTC()
    {
        return _createdUTC;
    }

    /**
     * @param createdUTC the _createdUTC to set
     */
    public void setCreatedUTC(Date createdUTC)
    {
        this._createdUTC = createdUTC;
    }

    /**
     * @return the _createdByUserId
     */
    public int getCreatedByUserId()
    {
        return _createdByUserId;
    }

    /**
     * @param createdByUserId the _createdByUserId to set
     */
    public void setCreatedByUserId(int createdByUserId)
    {
        this._createdByUserId = createdByUserId;
    }

    /**
     * @return the _isObligatory
     */
    public boolean isIsObligatory()
    {
        return _isObligatory;
    }

    /**
     * @param isObligatory the _isObligatory to set
     */
    public void setIsObligatory(boolean isObligatory)
    {
        this._isObligatory = isObligatory;
    }

    /**
     * @return the _assessmentId
     */
    public Integer getAssessmentId()
    {
        return _assessmentId;
    }

    /**
     * @param assessmentId the _assessmentId to set
     */
    public void setAssessmentId(Integer assessmentId)
    {
        this._assessmentId = assessmentId;
    }

    /**
     * @return the _assessmentStatusId
     */
    public Integer getAssessmentStatusId()
    {
        return _assessmentStatusId;
    }

    /**
     * @param assessmentStatusId the _assessmentStatusId to set
     */
    public void setAssessmentStatusId(Integer assessmentStatusId)
    {
        this._assessmentStatusId = assessmentStatusId;
    }

}
