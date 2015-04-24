package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import itslearning.platform.restApi.sdk.common.entities.Constants.SimpleStatusType;
import java.util.Date;

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
        /**
         * @deprecated Replaced with Score.
         */
        @Deprecated
        SimplePercentScore,
        SimpleStatus,
        Score
    }

    private Integer assessmentItemId;
    private String assessmentItemTitle;
    private Integer numberOfTimesRead;
    private Integer assessmentStatusItemId;
    private String assessmentStatusItemTitle;
    private Double simplePercentScore;
    private Double score;
    private SimpleStatusType simpleStatus;
    private String comment;
    private Integer _numberOfAttemptsTaken;
    private Integer _attemptId;
    private Date _reviewedUtc;
    private Integer _reviewedBy;
    private Integer _collaborationId;

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
     * @deprecated Replaced with Score.
     * @return the simplePercentScore
     */
    @Deprecated
    public Double getSimplePercentScore()
    {
        return simplePercentScore;
    }

    /**
     * Percent score to use when learning tool is created with simple assessment
     * @deprecated Replaced with Score.
     * @param simplePercentScore the simplePercentScore to set
     */
    @Deprecated
    public void setSimplePercentScore(Double simplePercentScore)
    {
        this.simplePercentScore = simplePercentScore;
    }
    
    /**
     * Score to use when learning tool using score assessment scale
     * @return the score
     */
    public Double getScore()
    {
        return score;
    }

    /**
     * Score to use when learning tool using score assessment scale
     * @param score the score to set
     */
    public void setScore(Double score)
    {
        this.score = score;
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
     * Comment about e.g. the work a student has done - assignment feedback etc
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
    
    /**
     * @return Number of attempts taken.
     */
    public Integer getNumberOfAttemptsTaken()
    {
        return _numberOfAttemptsTaken;
    }

    /**
     * @param numberOfAttemptsTaken Number of attempts taken.
     */
    public void setNumberOfAttemptsTaken(Integer numberOfAttemptsTaken)
    {
        _numberOfAttemptsTaken = numberOfAttemptsTaken;
    }
    
    /**
     * @return The Id of the counting attempt.
     */
    public Integer getAttemptId()
    {
        return _attemptId;
    }

    /**
     * @param attemptId The Id of the counting attempt.
     */
    public void setAttemptId(Integer attemptId)
    {
        _attemptId = attemptId;
    }
    
    /**
     * @return The assessment date.
     */
    public Date getReviewedUtc()
    {
        return _reviewedUtc;
    }

    /**
     * @param reviewedUtc The assessment date.
     */
    public void setReviewedUtc(Date reviewedUtc)
    {
        _reviewedUtc = reviewedUtc;
    }
    
    /**
     * @return The ID of user who made the assessment.
     */
    public Integer getReviewedBy()
    {
        return _reviewedBy;
    }

    /**
     * 
     * @param reviewedBy The ID of user who made the assessment.
     */
    public void setReviewedBy(Integer reviewedBy)
    {
        _reviewedBy = reviewedBy;
    }
    
    /**
     * @return Id of the collaboration (group) user chose to submit answer on behalf of.
     */
    public Integer getCollaborationId()
    {
        return _collaborationId;
    }

    /**
     * @param collaborationId Id of the collaboration (group) user chose to submit answer on behalf of.
     */
    public void setCollaborationId(Integer collaborationId)
    {
        _collaborationId = collaborationId;
    }
}
