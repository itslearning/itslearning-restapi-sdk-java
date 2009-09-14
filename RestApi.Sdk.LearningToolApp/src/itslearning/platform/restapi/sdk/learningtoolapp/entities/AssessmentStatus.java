package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Assessment status entity.
 * @author Amund Trovåg
 */
public class AssessmentStatus
{

    private int assessmentStatusId;
    private String title;

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
