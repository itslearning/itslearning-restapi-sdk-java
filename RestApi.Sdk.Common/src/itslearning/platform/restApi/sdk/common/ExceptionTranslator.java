/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Amund
 */
class ExceptionTranslator
{

    public static Exception fromHttpStatus(HttpStatusWrapper httpStatusWrapper)
    {
        Exception ex;
        if (httpStatusWrapper.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0)
        {
            ex = unauthorizedFromHttpStatus(httpStatusWrapper);
        } else if (httpStatusWrapper.getStatusCode().compareTo(HttpStatus.NOT_ACCEPTABLE) == 0)
        {
            ex = notAcceptableFromHttpStatus(httpStatusWrapper);
        } else if (httpStatusWrapper.getStatusCode().compareTo(HttpStatus.INTERNAL_SERVER_ERROR) == 0)
        {
            ex = internalServerErrorFromHttpStatus(httpStatusWrapper);
        } else
        {
            ex = new Exception(String.format("%s: %s", httpStatusWrapper.getStatusCode(), httpStatusWrapper.getDescription()));
        }

        return ex;
    }

    private static Exception unauthorizedFromHttpStatus(HttpStatusWrapper httpStatus)
    {
        // TODO: Get httpstatus text
        return new SecurityException();
    }

    private static Exception notAcceptableFromHttpStatus(HttpStatusWrapper httpStatus)
    {
        // TODO: Get httpstatus text
        return new IllegalArgumentException();
    }

    private static Exception internalServerErrorFromHttpStatus(HttpStatusWrapper httpStatus)
    {
        // TODO: Get httpstatus text

        return new IllegalArgumentException();
    }
}
