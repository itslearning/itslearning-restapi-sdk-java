package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.RequestParamsHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import itslearning.platform.restApi.sdk.common.Settings.IApplicationSettings;
import itslearning.platform.restapi.sdk.learningtoolapp.ViewLearningToolRequestParams;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Helper class for communication with it's learning API.
 * @author Amund Trovåg
 */
public class CommunicationHelper
{

    /**
     * Establishes communication with it's learning API and stores sessionId received from it's learning into the session state.
     * @param page
     * @param settings
     */
    public static void initApiSession(HttpServletRequest request, IApplicationSettings settings)
    {
        ViewLearningToolRequestParams parameters = getParams(request.getQueryString());
        try
        {
            // Validate if the request is not expired and if the signature is valid
            validateQueryString(request.getRequestURI(), settings.getSharedSecret(), settings.getRequestLifetimeInMinutes(), parameters);
        } catch (Exception ex)
        {
            Logger.getLogger(CommunicationHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Store received parameters to the session state
        storeParametersToSession(parameters, request.getSession(), settings.getApplicationKey(), settings.getSharedSecret());
    }

    private static ViewLearningToolRequestParams getParams(String queryString)
    {
        // TODO: 
        return null;
    //return new RequestParamsHandler<ViewLearningToolRequestParams>().getParams(queryString);
    }

    static void validateQueryString(String queryString, String sharedSecret, int requestLifetimeInMinutes, ViewLearningToolRequestParams parameters)
    {
        if (queryString == null || queryString.isEmpty())
        {
            throw new RuntimeException("queryString");
        }
        if (sharedSecret == null || sharedSecret.isEmpty())
        {
            throw new RuntimeException("sharedSecret");
        }
        if (parameters == null)
        {
            throw new RuntimeException("parameters");
        }

        // Check if timestamp is correct and within acceptable range
        if (parameters.getTimestamp() == null || parameters.getTimestamp().isEmpty())
        {
            throw new RuntimeException("Timestamp is not specified.");
        }

        //GregorianCalendar timestamp = new GregorianCalendar().setTimeInMillis();//parameters.getTimestamp());

        Calendar resultdate;
        try
        {
            resultdate = new GregorianCalendar();
            resultdate.setTimeInMillis(Long.parseLong(parameters.getTimestamp()));
        } catch (NumberFormatException nfe)
        {
            throw new RuntimeException("Query string has an invalid timestamp (check that UTC time is passed and that server time is correct)");
        }

        Calendar now = GregorianCalendar.getInstance();
        now.setTimeInMillis(now.getTimeInMillis()-requestLifetimeInMinutes);
        Calendar tooLate = GregorianCalendar.getInstance();
        tooLate.setTimeInMillis(now.getTimeInMillis()+requestLifetimeInMinutes);
        if(resultdate.before(now)){

        }
        if (timestamp < DateTime.UtcNow.AddMinutes(-requestLifetimeInMinutes) || timestamp > DateTime.UtcNow.AddMinutes(requestLifetimeInMinutes))
        {
            throw new SecurityAccessDeniedException("Query string has an invalid timestamp (check that UTC time is passed and that server time is correct)");
        }

        // Check signature
        if (string.IsNullOrEmpty(parameters.Signature))
        {
            throw new SecurityAccessDeniedException("Signature is not specified.");
        }

        // Remove signature from the querystring
        queryString = HttpUtility.UrlDecode(queryString);
        string queryStringWithoutSignature = queryString.Replace(string.Format("&Signature={0}", parameters.Signature), string.Empty);

        if (CryptographyHelper.ComputeHash(queryStringWithoutSignature + sharedSecret) != parameters.Signature)
        {
            throw new SecurityAccessDeniedException("Signature is invalid.");
        }
    }
}
