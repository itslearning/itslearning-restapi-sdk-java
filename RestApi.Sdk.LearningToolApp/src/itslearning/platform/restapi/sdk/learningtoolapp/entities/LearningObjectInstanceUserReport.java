package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import itslearning.platform.restApi.sdk.common.entities.Constants.SimpleStatusType;

/**
 * Learning object instance user report entity for RestApi clients.
 * @author Amund Trovåg
 */
public class LearningObjectInstanceUserReport extends LearningObjectInstanceUser {
    
    /**
     * Property names to order by.
     */
    public enum OrderBy 
    {
        None,
        FirstName,
        LastName,
        AssessmentItemTitle,
        NumberOfTimesRead,
        AssessmentStatusItemTitle,
        SimplePercentScore,
        SimpleStatus
    }

    private Integer assessmentItemId;
    private String assessmentItemTitle;
    private Integer numberOfTimesRead;
    private Integer assessmentStatusItemId;
    private String assessmentStatusItemTitle;
    private Double simplePercentScore;
    private SimpleStatusType simpleStatus;
    private String comment;    
    

    /**
     * UserInfo's grade Id.
     * @return the assessmentItemId
     */
    public Integer getAssessmentItemId()
    {
        return assessmentItemId;
    }

    /**
     * UserInfo's grade Id.
     * @param assessmentItemId the assessmentItemId to set
     */
    public void setAssessmentItemId(Integer assessmentItemId)
    {
        this.assessmentItemId = assessmentItemId;
    }

    /**
     * UserInfo's grade text description.
     * @return the assessmentItemTitle
     */
    public String getAssessmentItemTitle()
    {
        return assessmentItemTitle;
    }

    /**
     * UserInfo's grade text description.
     * @param assessmentItemTitle the assessmentItemTitle to set
     */
    public void setAssessmentItemTitle(String assessmentItemTitle)
    {
        this.assessmentItemTitle = assessmentItemTitle;
    }

    /**
     * Number of time learning object has been viewed.
     * @return the numberOfTimesRead
     */
    public Integer getNumberOfTimesRead()
    {
        return numberOfTimesRead;
    }

    /**
     * Number of time learning object has been viewed.
     * @param numberOfTimesRead the numberOfTimesRead to set
     */
    public void setNumberOfTimesRead(Integer numberOfTimesRead)
    {
        this.numberOfTimesRead = numberOfTimesRead;
    }

    /**
     * Assessment status Id.
     * @return the assessmentStatusItemId
     */
    public Integer getAssessmentStatusItemId()
    {
        return assessmentStatusItemId;
    }

    /**
     * Assessment status Id.
     * @param assessmentStatusItemId the assessmentStatusItemId to set
     */
    public void setAssessmentStatusItemId(Integer assessmentStatusItemId)
    {
        this.assessmentStatusItemId = assessmentStatusItemId;
    }

    /**
     * Assessment status text description.
     * @return the assessmentStatusItemTitle
     */
    public String getAssessmentStatusItemTitle()
    {
        return assessmentStatusItemTitle;
    }

    /**
     * Assessment status text description.
     * @param assessmentStatusItemTitle the assessmentStatusItemTitle to set
     */
    public void setAssessmentStatusItemTitle(String assessmentStatusItemTitle)
    {
        this.assessmentStatusItemTitle = assessmentStatusItemTitle;
    }

    /**
     * Percent score to use when learning tool is created with simple assessment
     * @return the simplePercentScore
     */
    public Double getSimplePercentScore()
    {
        return simplePercentScore;
    }

    /**
     * Percent score to use when learning tool is created with simple assessment
     * @param simplePercentScore the simplePercentScore to set
     */
    public void setSimplePercentScore(Double simplePercentScore)
    {
        this.simplePercentScore = simplePercentScore;
    }

    /**
     * Status to use when learning tool is created with simple assessment
     * @return the simpleStatus
     */
    public SimpleStatusType getSimpleStatus()
    {
        return simpleStatus;
    }

    /**
     * Status to use when learning tool is created with simple assessment
     * @param simpleStatus the simpleStatus to set
     */
    public void setSimpleStatus(SimpleStatusType simpleStatus)
    {
        this.simpleStatus = simpleStatus;
    }

    /**
     * Comment about e.g. the work a student has done - assignemnt feedback etc
     * @return the comment
     */
    public String getComment()
    {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    

}
