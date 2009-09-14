package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Assessment item entity.
 * @author Amund Trovåg
 */
public class AssessmentItem
{
    /**
     * Assessment item Id.
     */
    private int _assessmentItemId;
    /**
     * Id of the Assessment the item belongs to.
     */
    private int _assessmentId;
    /**
     * Assessment item title.
     */
    private String _title;
    /**
     * Assessment item description.
     */
    private String _description;
    /**
     * Upper border of the assessment percent range, exclusive.
     */
    private double _percentTo;
    /**
     * Lower border of the assessment percent range, inclusive.
     */
    private double _percentFromAndIncl;

    /**
     * @return the _assessmentItemId
     */
    public int getAssessmentItemId()
    {
        return _assessmentItemId;
    }

    /**
     * @param assessmentItemId the _assessmentItemId to set
     */
    public void setAssessmentItemId(int assessmentItemId)
    {
        this._assessmentItemId = assessmentItemId;
    }

    /**
     * @return the _assessmentId
     */
    public int getAssessmentId()
    {
        return _assessmentId;
    }

    /**
     * @param assessmentId the _assessmentId to set
     */
    public void setAssessmentId(int assessmentId)
    {
        this._assessmentId = assessmentId;
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
     * @return the _description
     */
    public String getDescription()
    {
        return _description;
    }

    /**
     * @param description the _description to set
     */
    public void setDescription(String description)
    {
        this._description = description;
    }

    /**
     * @return the _percentTo
     */
    public double getPercentTo()
    {
        return _percentTo;
    }

    /**
     * @param percentTo the _percentTo to set
     */
    public void setPercentTo(double percentTo)
    {
        this._percentTo = percentTo;
    }

    /**
     * @return the _percentFromAndIncl
     */
    public double getPercentFromAndIncl()
    {
        return _percentFromAndIncl;
    }

    /**
     * @param percentFromAndIncl the _percentFromAndIncl to set
     */
    public void setPercentFromAndIncl(double percentFromAndIncl)
    {
        this._percentFromAndIncl = percentFromAndIncl;
    }
}
