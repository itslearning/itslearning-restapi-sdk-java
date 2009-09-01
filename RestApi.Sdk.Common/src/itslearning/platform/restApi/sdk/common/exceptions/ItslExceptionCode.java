/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restApi.sdk.common.exceptions;

/**
 * Error codes for business logic exceptions.
 * @author Amund
 */
public enum ItslExceptionCode
{
        Unknown,
        ObjectNotFound,
        UniqueViolation,
        LearningToolNotAllowComments,
        LearningToolNotAllowSettingAssesment,
        InvalidOperation,
        VersionDoesNotExist
}