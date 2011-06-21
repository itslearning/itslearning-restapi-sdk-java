package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import itslearning.platform.restApi.sdk.common.entities.Constants.SimpleStatusType;

/**
 * Learning object instance user report entity for RestApi clients.
 * @author Amund Trovåg
 */
public class LearningObjectInstanceUserReport {

    private int userId;
    private String firstName;
    private String lastName;
    private Integer assessmentItemId;
    private String assessmentItemTitle;
    private Integer numberOfTimesRead;
    private Integer assessmentStatusItemId;
    private String assessmentStatusItemTitle;
    private Double simplePercentScore;
    private SimpleStatusType simpleStatus;
    private String comment;
    private String email;
    private String mobile;
    private String syncKey;
    private String custom1Id;
    private String custom2Id;
    private String custom3Id;
    private String custom4Id;
    private String custom5Id;
    private String custom1;
    private String custom2;
    private String custom3;
    private String custom4;
    private String custom5;
    
    
    /**
     * @return the userId
     */
    public int getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

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
    
    /**
     * User email address.
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    /**
     * User mobile phone.
     * @return the mobile
     */
    public String getMobile()
    {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    /**
     * User sync key.
     * @return the syncKey
     */
    public String getSyncKey()
    {
        return syncKey;
    }

    /**
     * @param syncKey the syncKey to set
     */
    public void setSyncKey(String syncKey)
    {
        this.syncKey = syncKey;
    }
    
    /**
     * Custom field 1 identifier defined on site.
     * @return the custom1Id
     */
    public String getCustom1Id()
    {
        return custom1Id;
    }

    /**
     * @param custom1Id the custom1Id to set
     */
    public void setCustom1Id(String custom1Id)
    {
        this.custom1Id = custom1Id;
    }
    
    /**
     * Custom field 2 identifier defined on site.
     * @return the custom2Id
     */
    public String getCustom2Id()
    {
        return custom2Id;
    }

    /**
     * @param custom2Id the custom2Id to set
     */
    public void setCustom2Id(String custom2Id)
    {
        this.custom2Id = custom2Id;
    }
    
    /**
     * Custom field 3 identifier defined on site.
     * @return the custom3Id
     */
    public String getCustom3Id()
    {
        return custom3Id;
    }

    /**
     * @param custom3Id the custom3Id to set
     */
    public void setCustom3Id(String custom3Id)
    {
        this.custom3Id = custom3Id;
    }
    
     /**
     * Custom field 4 identifier defined on site.
     * @return the custom4Id
     */
    public String getCustom4Id()
    {
        return custom4Id;
    }

    /**
     * @param custom4Id the custom4Id to set
     */
    public void setCustom4Id(String custom4Id)
    {
        this.custom4Id = custom4Id;
    }
    
    /**
     * Custom field 5 identifier defined on site.
     * @return the custom5Id
     */
    public String getCustom5Id()
    {
        return custom5Id;
    }

    /**
     * @param custom5Id the custom5Id to set
     */
    public void setCustom5Id(String custom5Id)
    {
        this.custom5 = custom5;
    }
    
    /**
     * Custom field 1 value for the user.
     * @return the custom1Id
     */
    public String getCustom1()
    {
        return custom1;
    }

    /**
     * @param custom1 the custom1Id to set
     */
    public void setCustom1(String custom1)
    {
        this.custom1 = custom1;
    }
    
    /**
     * Custom field 2 value for the user.
     * @return the custom2
     */
    public String getCustom2()
    {
        return custom2;
    }

    /**
     * @param custom2Id the custom2Id to set
     */
    public void setCustom2(String custom2)
    {
        this.custom2 = custom2;
    }
    
    /**
     * Custom field 3 value for the user.
     * @return the custom3
     */
    public String getCustom3()
    {
        return custom3;
    }

    /**
     * @param custom3 the custom3 to set
     */
    public void setCustom3(String custom3)
    {
        this.custom3 = custom3;
    }
    
     /**
     * Custom field 4 value for the user.
     * @return the custom4
     */
    public String getCustom4()
    {
        return custom4;
    }

    /**
     * @param custom4 the custom4 to set
     */
    public void setCustom4(String custom4)
    {
        this.custom4 = custom4;
    }
    
    /**
     * Custom field 5 value for the user.
     * @return the custom5
     */
    public String getCustom5()
    {
        return custom5;
    }

    /**
     * @param custom5 the custom5 to set
     */
    public void setCustom5(String custom5)
    {
        this.custom5 = custom5;
    }
}
