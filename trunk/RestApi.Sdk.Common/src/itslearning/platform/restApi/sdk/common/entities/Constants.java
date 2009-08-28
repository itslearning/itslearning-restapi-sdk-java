/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common.entities;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Amund Trovåg
 */
public class Constants
{

    public static final String UnauthorizedStatusDescription = "Unauthorized";

    public enum LearningObjectInstancePermissions implements Serializable
    {

        /**
         * No permissions
         */
        NONE(0),
        /**
         * Can read
         */
        READ(1),
        /**
         * Can participate (e.g. submit answer) - also automatically gives access to Read
         */
        PARTICIPATE(2),
        /**
         * Can evalutate (e.g. evaluate answer and set assessment) - also automatically gives access to Read
         */
        EVALUATE(4),
        /**
         * Can modifiy (change content and delete) - also automatically gives access to Read and Participate
         */
        MODIFY(8);
        private static final Map<Integer, LearningObjectInstancePermissions> lookup = new HashMap<Integer, LearningObjectInstancePermissions>();


        static
        {
            for (LearningObjectInstancePermissions s : EnumSet.allOf(LearningObjectInstancePermissions.class))
            {
                lookup.put(s.getCode(), s);
            }
        }
        private int code;

        private LearningObjectInstancePermissions(int code)
        {
            this.code = code;
        }

        public int getCode()
        {
            return code;
        }

        public static LearningObjectInstancePermissions get(int code)
        {
            return lookup.get(code);
        }
    }

    /**
     *  Status will be displayed in task list in it's learning and also shows in reports in course
     */
    public enum SimpleStatusType implements Serializable
    {

        /**
         * Use NotStarted to specify that user has not started to work on learning object instance
         */
        NOTSTARTED,
        /**
         *  Use OnGoing to specify that user has started to work on learning object instance, but is not done yet
         */
        ONGOING,
        /**
         * Use Completed to specify that user is done working on learning object instance
         */
        COMPLETED;
    }

    /**
     * Assessment types.
     */
    public enum AssessmentType implements Serializable
    {

        /**
         * Tool doesn't support assessment.
         */
        NONE,
        /**
         * Simple assessment type.
         */
        SIMPLE,
        /**
         * Advanced assesment type.
         */
        ADVANCED;
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
        LEARNINGRESOURCE,
        /**
         * Learning activity (like Test or Assignment).
         */
        LEARNINGACTIVITY;
    }
}
