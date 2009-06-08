/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp;

/**
 *
 * @author amund
 */
public class Constants
{
    public abstract class ErrorMessages
    {
        /// <exclude/>
        public final String SessionIdNotSpecified = "SessionId is not specified or session is expired.";
        /// <exclude/>
        public final String LearningObjectIdNotSpecified = "Learning Object Id is not specified.";
        /// <exclude/>
        public final String LearningObjectInstanceIdNotSpecified = "Learning Object Instance Id is not specified.";
        /// <exclude/>
        public final String VersionNotSpecified = "Version is not specified.";
        /// <exclude/>
        public final String LanguageNotSpecified = "Language is not specified.";
        /// <exclude/>
        public final String PermissionsNotSpecified = "Permissions are not specified.";
    }
    public abstract class SessionKeys
    {
        public final String ApiSession = "ApiSession";
        public final String ApiSessionId = "ApiSessionId";
        public final String Permissions = "Permissions";
        public final String UserInfo = "UserInfo";
    }
}
