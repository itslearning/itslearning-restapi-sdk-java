package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 *
 * @author Yaroslav.Kulik
 */
public class LearningObjectiveAssessment {
    private int _learningObjectiveId;
    private int _userId;
    private int _rubricCriteriaItemId;
    private double _percentScore;
    private String _comment;
    private boolean _mastery;
    private boolean _override;
    private Integer _assessedAchievementLevelId;
    private boolean _reportable;
    
    /**
     * @return The learning objective identifier.
     */
    public int getLearningObjectiveId() {
        return _learningObjectiveId;
    }

    /**
     * @param learningObjectiveId The learning objective identifier.
     */
    public void setLearningObjectiveId(int learningObjectiveId) {
        _learningObjectiveId = learningObjectiveId;
    }
    
    /**
     * @return Identifier of the user assessments belong to.
     */
    public int getUserId() {
        return _userId;
    }

    /**
     * @param userId Identifier of the user assessments belong to.
     */
    public void setUserId(int userId) {
        _userId = userId;
    }
    
    /**
     * @return Identifier of the rubric criteria item.
     */
    public int getRubricCriteriaItemId() {
        return _rubricCriteriaItemId;
    }

    /**
     * @param rubricCriteriaItemId Identifier of the rubric criteria item.
     */
    public void setRubricCriteriaItemId(int rubricCriteriaItemId) {
        _rubricCriteriaItemId = rubricCriteriaItemId;
    }
    
    /**
     * @return Score in percent of the assessment.
     */
    public double getPercentScore() {
        return _percentScore;
    }

    /**
     * @param percentScore Score in percent of the assessment.
     */
    public void setPercentScore(double percentScore) {
        _percentScore = percentScore;
    }
    
    /**
     * @return Assessment comment.
     */
    public String getComment() {
        return _comment;
    }

    /**
     * @param comment Assessment comment.
     */
    public void setComment(String comment) {
        _comment = comment;
    }
    
    /**
     * @return Defines if mastery is in use.
     */
    public boolean getMastery() {
        return _mastery;
    }

    /**
     * @param mastery Defines if mastery is in use.
     */
    public void setMastery(boolean mastery) {
        _mastery = mastery;
    }
    
    /**
     * @return Defines if assessment overrides all criteria assessments for the element.
     */
    public boolean getOverride() {
        return _override;
    }

    /**
     * @param override Defines if assessment overrides all criteria assessments for the element.
     */
    public void setOverride(boolean override) {
        _override = override;
    }
    
    /**
     * @return Identifier of the assessed level.
     */
    public Integer getAssessedAchievementLevelId() {
        return _assessedAchievementLevelId;
    }

    /**
     * @param assessedAchievementLevelId Identifier of the assessed level.
     */
    public void setAssessedAchievementLevelId(Integer assessedAchievementLevelId) {
        _assessedAchievementLevelId = assessedAchievementLevelId;
    }
    
    /**
     * @return Gets or sets if the assessment is reportable.
     */
    public boolean getReportable() {
        return _reportable;
    }

    /**
     * @param reportable Gets or sets if the assessment is reportable.
     */
    public void setReportable(boolean reportable) {
        _reportable = reportable;
    }
}
