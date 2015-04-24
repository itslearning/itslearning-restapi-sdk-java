package itslearning.platform.restapi.sdk.learningtoolapp.entities;
import java.util.HashMap;
import java.util.List;

/**
 * Learning objective report settings.
 * 
 * @author Yaroslav.Kulik
 */
public class LearningObjectiveReportSettings {
    private AchievementLevelOrder _achievementLevelOrder;
    private boolean _useMastery;
    private LearningObjectiveMasteryRecurrenceType _learningObjectiveMasteryRecurrenceType;
    private List<LearningObjectiveAssessmentStatus> _statuses;
    private HashMap<Integer, LearningObjectiveMasteryClientSettings> _clientMasterySettings;
    private boolean _showReportStatusForStudents;
    private boolean _connectAssessmentCriteriaToScale;
    
    /**
     * @return Order of achievement levels.
     */
    public AchievementLevelOrder getAchievementLevelOrder() {
        return _achievementLevelOrder;
    }

    /**
     * @param achievementLevelOrder Order of achievement levels.
     */
    public void setAchievementLevelOrder(AchievementLevelOrder achievementLevelOrder) {
        _achievementLevelOrder = achievementLevelOrder;
    }
    
    /**
     * @return Defines whether to use mastery or not.
     */
    public boolean getUseMastery() {
        return _useMastery;
    }

    /**
     * @param useMastery Defines whether to use mastery or not.
     */
    public void setUseMastery(boolean useMastery) {
        _useMastery = useMastery;
    }
    
    /**
     * @return Type of the learning objective mastery recurrence.
     */
    public LearningObjectiveMasteryRecurrenceType getLearningObjectiveMasteryRecurrenceType() {
        return _learningObjectiveMasteryRecurrenceType;
    }

    /**
     * @param learningObjectiveMasteryRecurrenceType Type of the learning objective mastery recurrence.
     */
    public void setLearningObjectiveMasteryRecurrenceType(LearningObjectiveMasteryRecurrenceType learningObjectiveMasteryRecurrenceType) {
        _learningObjectiveMasteryRecurrenceType = learningObjectiveMasteryRecurrenceType;
    }
    
    /**
     * @return The collection of all possible learning objective assessment statuses for given instance.
     */
    public List<LearningObjectiveAssessmentStatus> getStatuses() {
        return _statuses;
    }

    /**
     * @param statuses The collection of all possible learning objective assessment statuses for given instance.
     */
    public void setStatuses(List<LearningObjectiveAssessmentStatus> statuses) {
        _statuses = statuses;
    }
    
    /**
     * @return Dictionary of learning objective mastery settings for every learning objective (which IDs are dictionary keys).
     */
    public HashMap<Integer, LearningObjectiveMasteryClientSettings> getClientMasterySettings() {
        return _clientMasterySettings;
    }

    /**
     * @param clientMasterySettings Dictionary of learning objective mastery settings for every learning objective (which IDs are dictionary keys).
     */
    public void setClientMasterySettings(HashMap<Integer, LearningObjectiveMasteryClientSettings> clientMasterySettings) {
        _clientMasterySettings = clientMasterySettings;
    }
    
    /**
     * @return Determines whether report outcome status should be shown to students.
     */
    public boolean getShowReportStatusForStudents() {
        return _showReportStatusForStudents;
    }

    /**
     * @param showReportStatusForStudents Determines whether report outcome status should be shown to students.
     */
    public void setShowReportStatusForStudents(boolean showReportStatusForStudents) {
        _showReportStatusForStudents = showReportStatusForStudents;
    }
    
    /**
     * @return Defines whether to calculate overall assessment in view of the criteria-based assessment. An option from the assessment scale – such as a mark – will be suggested on the basis of the criteria-based assessment if you use both to assess an activity.
     */
    public boolean getConnectAssessmentCriteriaToScale() {
        return _connectAssessmentCriteriaToScale;
    }

    /**
     * @param connectAssessmentCriteriaToScale Defines whether to calculate overall assessment in view of the criteria-based assessment. An option from the assessment scale – such as a mark – will be suggested on the basis of the criteria-based assessment if you use both to assess an activity.
     */
    public void setConnectAssessmentCriteriaToScale(boolean connectAssessmentCriteriaToScale) {
        _connectAssessmentCriteriaToScale = connectAssessmentCriteriaToScale;
    }
}
