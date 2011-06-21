/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp;

/**
 *
 * @author Amund Trovag
 */
public class Constants
{

    public abstract class ErrorMessages
    {
        /// <exclude/>

        public final static String SessionIdNotSpecified = "SessionId is not specified or session is expired.";
        /// <exclude/>
        public final static String LearningObjectIdNotSpecified = "Learning Object Id is not specified.";
        /// <exclude/>
        public final static String LearningObjectInstanceIdNotSpecified = "Learning Object Instance Id is not specified.";
        /// <exclude/>
        public final static String VersionNotSpecified = "Version is not specified.";
        /// <exclude/>
        public final static String LanguageNotSpecified = "Language is not specified.";
        /// <exclude/>
        public final static String PermissionsNotSpecified = "Permissions are not specified.";
    }

    public abstract class SessionKeys
    {
        public final static String ApiSession = "ApiSession";
        public final static String ApiSessionId = "ApiSessionId";
        public final static String Permissions = "Permissions";
        public final static String UserInfo = "UserInfo";
    }
}
