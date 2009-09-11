/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
