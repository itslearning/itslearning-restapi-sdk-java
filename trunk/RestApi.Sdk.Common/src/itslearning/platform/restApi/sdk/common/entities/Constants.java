/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common.entities;

import java.io.Serializable;

/**
 *
 * @author Amund Trovåg
 */
class Constants
{

    public static final String UnauthorizedStatusDescription = "Unauthorized";

    public class LearningObjectInstancePermissions implements Serializable
    {

        /**
         * No permissions
         */
        public static final int NONE = 0;
        /**
         * Can read
         */
        public static final int READ = 1;
        /**
         * Can participate (e.g. submit answer) - also automatically gives access to Read
         */
        public static final int PARTICIPATE = 2;
        /**
         * Can evalutate (e.g. evaluate answer and set assessment) - also automatically gives access to Read
         */
        public static final int EVALUATE = 4;
        /**
         * Can modifiy (change content and delete) - also automatically gives access to Read and Participate
         */
        public static final int MODIFY = 8;
    }

    /**
     *  Status will be displayed in task list in it's learning and also shows in reports in course
     */
    public class SimpleStatusType implements Serializable
    {

        /**
         * Use NotStarted to specify that user has not started to work on learning object instance
         */
        public static final int NOTSTARTED = 1;
        /**
         *  Use OnGoing to specify that user has started to work on learning object instance, but is not done yet
         */
        public static final int ONGOING = 2;
        /**
         * Use Completed to specify that user is done working on learning object instance
         */
        public static final int COMPLETED = 3;
    }

    /**
     * Assessment types.
     */
    public class AssessmentType implements Serializable
    {

        /**
         * Tool doesn't support assessment.
         */
        public static final int NONE = 0;
        /**
         * Simple assessment type.
         */
        public static final int SIMPLE = 1;
        /**
         * Advanced assesment type.
         */
        public static final int ADVANCED = 2;
    }

    /**
     * Types of the learning tools.
     * @author Amund
     */
    public class LearningToolType implements Serializable
    {
        /**
         * Learning resource (like Note).
         */
        public static final int LEARNINGRESOURCE = 0;
        /**
         * Learning activity (like Test or Assignment).
         */
        public static final int LEARNINGACTIVITY = 1;
    }
}
