package itslearning.platform.restApi.sdk.common.entities;

import java.io.Serializable;

/**
 *
 * @author Amund Trovåg
 */
public class Constants
{

    public static final String UnauthorizedStatusDescription = "Unauthorized";

    /**
     *  Status will be displayed in task list in it's learning and also shows in reports in course
     */
    public enum SimpleStatusType implements Serializable
    {

        /**
         * Use NotStarted to specify that user has not started to work on learning object instance
         */
        NotStarted,
        /**
         *  Use OnGoing to specify that user has started to work on learning object instance, but is not done yet
         */
        OnGoing,
        /**
         * Use Completed to specify that user is done working on learning object instance
         */
        Completed;
    }

    /**
     * Assessment types.
     */
    public enum AssessmentType implements Serializable
    {

        /**
         * Tool doesn't support assessment.
         */
        None,
        /**
         * Simple assessment type.
         */
        Simple,
        /**
         * Advanced assesment type.
         */
        Advanced;
    }

    /**
     * Types of the learning tools.
     * @author Amund
     */
    public enum LearningToolType implements Serializable
    {

        /**
         * Learning resource (like Note).
         */
        LearningResource,
        /**
         * Learning activity (like Test or Assignment).
         */
        LearningActivity;
    }

    /**
     * Defines a user's role in an organization/hierarchy in it's learning
     */
    public enum UserRole implements Serializable
    {
        /**
         * A guest in an organization
         */
        Guest,
        /**
         * A learner in an organization
         */
        Learner,
        /**
         * Staff (i.e. teacher etc) in an organization
         */
        Staff;
    }

    public enum ElementPermission implements Serializable
    {
        All,    // All = You have all the below permissions.
        Modifier,
        Evaluator,
        Participant,
        Read;
    }

    /**
     * Defines types of organisations
     */
    public enum OrganisationType implements Serializable
    {
        Site,
        School,
        // For forwardscompatibility. Only set if itslearning adds values to the type and SDK is not updated on a learningTool
        Unknown
    }
    
    /**
     * Defines types of education segments
     */
    public enum EducationSegment implements Serializable
    {
        Higher,
        Secondary,
        Primary,
        Other
    }
}
