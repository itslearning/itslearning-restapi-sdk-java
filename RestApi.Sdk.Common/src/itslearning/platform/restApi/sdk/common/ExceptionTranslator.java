/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;


/**
 *
 * @author Amund
 */
// TODO: Fix this with org.springframework.http.httpstatus enum
class ExceptionTranslator
{

    public static Exception fromHttpStatus(HttpStatus httpStatus)
    {
        Exception ex;
        if (httpStatus.getStatusCode().compareTo(org.springframework.http.HttpStatus.UNAUTHORIZED) == 0)
        {
            ex = unauthorizedFromHttpStatus(httpStatus);
        } else if (httpStatus.getStatusCode().compareTo(org.springframework.http.HttpStatus.NOT_ACCEPTABLE) == 0)
        {
            ex = notAcceptableFromHttpStatus(httpStatus);
        } else if (httpStatus.getStatusCode().compareTo(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR) == 0)
        {
            ex = internalServerErrorFromHttpStatus(httpStatus);
        } else
        {
            ex = new Exception(String.format("%s: %s",httpStatus.getStatusCode(), httpStatus.getDescription()));
        }

        return ex;
    }

    private static Exception unauthorizedFromHttpStatus(HttpStatus httpStatus)
    {
        // TODO: Get httpstatus text
        return new SecurityException();
    }

    private static Exception notAcceptableFromHttpStatus(HttpStatus httpStatus)
    {
        // TODO: Get httpstatus text
        return new IllegalArgumentException();
    }

    private static Exception internalServerErrorFromHttpStatus(HttpStatus httpStatus)
    {
        return new IllegalArgumentException();
    }
}
