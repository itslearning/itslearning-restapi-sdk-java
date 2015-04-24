package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Learning objective mastery client settings.
 *
 * @author Yaroslav.Kulik
 */
public class LearningObjectiveMasteryClientSettings {
    private boolean _affectsByLevel;
    private boolean _affectsByResettingMastery;
    private boolean _masteredWithoutOverride;
    private boolean _breaksRecurrence;
    private boolean _isReportMastered;
    
    /**
     * @return Affects by level.
     */
    public boolean getAffectsByLevel() {
        return _affectsByLevel;
    }

    /**
     * @param affectsByLevel Affects by level.
     */
    public void setAffectsByLevel(boolean affectsByLevel) {
        _affectsByLevel = affectsByLevel;
    }
    
    /**
     * @return Affects by resetting mastery.
     */
    public boolean getAffectsByResettingMastery() {
        return _affectsByResettingMastery;
    }

    /**
     * @param affectsByResettingMastery Affects by resetting mastery.
     */
    public void setAffectsByResettingMastery(boolean affectsByResettingMastery) {
        _affectsByResettingMastery = affectsByResettingMastery;
    }
    
    /**
     * @return Mastered without override.
     */
    public boolean getMasteredWithoutOverride() {
        return _masteredWithoutOverride;
    }

    /**
     * @param masteredWithoutOverride Mastered without override.
     */
    public void setMasteredWithoutOverride(boolean masteredWithoutOverride) {
        _masteredWithoutOverride = masteredWithoutOverride;
    }
    
    /**
     * @return Breaks the recurrence.
     */
    public boolean getBreaksRecurrence() {
        return _breaksRecurrence;
    }

    /**
     * @param breaksRecurrence Breaks the recurrence.
     */
    public void setBreaksRecurrence(boolean breaksRecurrence) {
        _breaksRecurrence = breaksRecurrence;
    }
    
    /**
     * @return Is report mastered.
     */
    public boolean getIsReportMastered() {
        return _isReportMastered;
    }

    /**
     * @param isReportMastered Is report mastered.
     */
    public void setIsReportMastered(boolean isReportMastered) {
        _isReportMastered = isReportMastered;
    }
}
