package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Learning objective assessment outcome to show in the report. The starting percentage must be defined for each status used.
 * 
 * @author Yaroslav.Kulik
 */
public class LearningObjectiveAssessmentStatus {
    private int _learningObjectiveAssessmentStatusId;
    private boolean _enabled;
    private int _startsAtPercentage;
    private String _label;
    private LearningObjectiveAssessmentStatusType _type;
    private boolean _masteryThreshold;
    
    /**
     * @return The ID of current status.
     */
    public int getLearningObjectiveAssessmentStatusId() {
        return _learningObjectiveAssessmentStatusId;
    }

    /**
     * @param learningObjectiveAssessmentStatusId The ID of current status.
     */
    public void setLearningObjectiveAssessmentStatusId(int learningObjectiveAssessmentStatusId) {
        _learningObjectiveAssessmentStatusId = learningObjectiveAssessmentStatusId;
    }
    
    /**
     * @return Defines whether current status is enabled.
     */
    public boolean getEnabled() {
        return _enabled;
    }

    /**
     * @param enabled Defines whether current status is enabled.
     */
    public void setEnabled(boolean enabled) {
        _enabled = enabled;
    }
    
    /**
     * @return Starting percentage of assessed answer, which will be mapped to current status (ending percentage is StartsAtPercentage of next status).
     */
    public int getStartsAtPercentage() {
        return _startsAtPercentage;
    }

    /**
     * @param startsAtPercentage Starting percentage of assessed answer, which will be mapped to current status (ending percentage is StartsAtPercentage of next status).
     */
    public void setStartsAtPercentage(int startsAtPercentage) {
        _startsAtPercentage = startsAtPercentage;
    }
    
    /**
     * @return Custom label, overriding how status should be written in the UI. NULL means that status text is not overriden.
     */
    public String getLabel() {
        return _label;
    }

    /**
     * @param label Custom label, overriding how status should be written in the UI. NULL means that status text is not overriden.
     */
    public void setLabel(String label) {
        _label = label;
    }
    
    /**
     * @return Type of status.
     */
    public LearningObjectiveAssessmentStatusType getType() {
        return _type;
    }

    /**
     * @param type Type of status.
     */
    public void setType(LearningObjectiveAssessmentStatusType type) {
        _type = type;
    }
    
    /**
     * @return Defines that if student report is assessed with current status (or any other status with StartsAtPercentage greater than current), then "Mastered" flag will be set.
     */
    public boolean getMasteryThreshold() {
        return _masteryThreshold;
    }

    /**
     * @param masteryThreshold Defines that if student report is assessed with current status (or any other status with StartsAtPercentage greater than current), then "Mastered" flag will be set.
     */
    public void setMasteryThreshold(boolean masteryThreshold) {
        _masteryThreshold = masteryThreshold;
    }
}
