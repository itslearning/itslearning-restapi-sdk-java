package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import java.util.List;

/**
 * Rubric criteria item entity
 * 
 * @author Yaroslav Kulik
 */
public class RubricCriteriaItem {
    private int _id;
    private String _title;
    private int _learningObjectiveId;
    private List<RubricAchievementLevel> _achievementLevels;
    private String _uniqueId;
    
    /**
     * @return Identifier of the rubric criteria.
     */
    public int getId() {
        return _id;
    }

    /**
     * @param id Identifier of the rubric criteria.
     */
    public void setId(int id) {
        _id = id;
    }
    
    /**
     * @return Title of the rubric criteria.
     */
    public String getTitle() {
        return _title;
    }

    /**
     * @param title Title of the rubric criteria.
     */
    public void setTitle(String title) {
        _title = title;
    }
    
    /**
     * @return Learning objective the rubric criteria is attached to.
     */
    public int getLearningObjectiveId() {
        return _learningObjectiveId;
    }

    /**
     * @param learningObjectiveId Learning objective the rubric criteria is attached to.
     */
    public void setLearningObjectiveId(int learningObjectiveId) {
        _learningObjectiveId = learningObjectiveId;
    }
    
    /**
     * @return The rubric criteria's achievement levels.
     */
    public List<RubricAchievementLevel> getAchievementLevels() {
        return _achievementLevels;
    }

    /**
     * @param achievementLevels The rubric criteria's achievement levels.
     */
    public void setAchievementLevels(List<RubricAchievementLevel> achievementLevels) {
        _achievementLevels = achievementLevels;
    }
    
    /**
     * @return Guid of the rubric criteria.
     */
    public String getUniqueId(String uniqueId) {
        return _uniqueId;
    }
    
    /**
     * @param uniqueId Guid of the rubric criteria.
     */
    public void setUniqueId(String uniqueId) {
        _uniqueId = uniqueId;
    }
}
