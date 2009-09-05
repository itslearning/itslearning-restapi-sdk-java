/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Assessment status item entity.
 * @author Amund Trovåg
 */
public class AssessmentStatusItem {

    private int assessmentStatusItemId;
    private int assessmentStatusId;
    private String title;

    /**
     * @return the assessmentStatusItemId
     */
    public int getAssessmentStatusItemId()
    {
        return assessmentStatusItemId;
    }

    /**
     * @param assessmentStatusItemId the assessmentStatusItemId to set
     */
    public void setAssessmentStatusItemId(int assessmentStatusItemId)
    {
        this.assessmentStatusItemId = assessmentStatusItemId;
    }

    /**
     * @return the assessmentStatusId
     */
    public int getAssessmentStatusId()
    {
        return assessmentStatusId;
    }

    /**
     * @param assessmentStatusId the assessmentStatusId to set
     */
    public void setAssessmentStatusId(int assessmentStatusId)
    {
        this.assessmentStatusId = assessmentStatusId;
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
}
