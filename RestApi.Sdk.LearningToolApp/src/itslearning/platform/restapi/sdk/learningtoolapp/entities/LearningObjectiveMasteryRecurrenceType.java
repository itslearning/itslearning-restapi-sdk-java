package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Contains possible Learning objectives assessment recurrence types.
 * 
 * @author Yaroslav.Kulik
 */
public enum LearningObjectiveMasteryRecurrenceType {
    NotInUse, // Not used in RestAPI
    None,
    TwoAssessmentsInRow,
    ThreeAssessmentsInRow,
    FourAssessmentsInRow,
    TwoAssessments,
    ThreeAssessments,
    FourAssessments
}
