package itslearning.platform.restApi.sdk.common;

import org.springframework.http.HttpStatus;

/**
 * Helper class for converting Exceptions to HTTP statuses and back. 
 * @author Amund
 */
public class ExceptionTranslator
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
            ex = new Exception(formatExceptionString(httpStatusWrapper));
        }

        return ex;
    }

    private static String formatExceptionString(HttpStatusWrapper httpStatus)
    {
        return String.format("%s: %s", httpStatus.getStatusCode().value(), httpStatus.getDescription());
    }

    private static Exception unauthorizedFromHttpStatus(HttpStatusWrapper httpStatus)
    {
        return new SecurityException(formatExceptionString(httpStatus));
    }

    private static Exception notAcceptableFromHttpStatus(HttpStatusWrapper httpStatus)
    {
        return new RuntimeException(formatExceptionString(httpStatus));
    }

    private static Exception internalServerErrorFromHttpStatus(HttpStatusWrapper httpStatus)
    {
        return new RuntimeException(formatExceptionString(httpStatus));
    }
}
