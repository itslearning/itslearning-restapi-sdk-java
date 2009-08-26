/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import java.util.List;

/**
 *
 * @author Amund Trovåg
 */
/// <summary>
/// Assessment entity.
/// </summary>
public class Assessment
{
    private int _assessmentId;
    private String _title;
    private String _description;

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


}

