package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.RequestParamsHandler;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import javax.servlet.http.*;
import itslearning.platform.restApi.sdk.common.Settings.IApplicationSettings;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restApi.sdk.common.entities.Constants.LearningObjectInstancePermissions;
import itslearning.platform.restApi.sdk.common.entities.UserInfo;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

/**
 * Helper class for communication with it's learning API.
 * @author Amund Trovåg
 */
public class CommunicationHelper
{

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Establishes communication with it's learning API and stores sessionId received from it's learning into the session state.
     * @param page
     * @param settings
     */
    public static void initApiSession(HttpServletRequest request, IApplicationSettings settings)
    {
        ViewLearningToolRequestParams parameters = getParams(request.getParameterMap());
        try
        {
            // Validate if the request is not expired and if the signature is valid
            validateQueryString(String.format("%s?%s", request.getRequestURL(),request.getQueryString()), settings.getSharedSecret(), settings.getRequestLifetimeInMinutes(), parameters);
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }

        // Store received parameters to the session state
        storeParametersToSession(parameters, request.getSession(), settings.getApplicationKey(), settings.getSharedSecret());
    }

    /**
     * Retrieves application internal Id of learning object from the query string of the specified page.
     * @param request
     * @return
     */
    public static int getLearningObjectId(HttpServletRequest request)
    {
        Integer temp = getParams(request.getParameterMap()).getLearningObjectId();
        if (temp == null)
        {
            throw new IllegalArgumentException(Constants.ErrorMessages.LearningObjectIdNotSpecified);
        }
        return temp.intValue();
    }

    /**
     *  Retrieves Id of learning object in Core from the query string of the specified page.
     * @param request
     * @return
     */
    public static int getLearningObjectInstanceId(HttpServletRequest request)
    {
        Integer temp = getParams(request.getParameterMap()).getLearningObjectInstanceId();
        if (temp == null)
        {
            throw new IllegalArgumentException(Constants.ErrorMessages.LearningObjectInstanceIdNotSpecified);
        }
        return temp.intValue();
    }

    /**
     * Retrives version parameter from the query string of the specified page.
     * @param request
     * @return
     */
    public static String getVersion(HttpServletRequest request)
    {
        String temp = getParams(request.getParameterMap()).getVersion();
        if (temp == null || temp.isEmpty())
        {
            throw new IllegalArgumentException(Constants.ErrorMessages.VersionNotSpecified);
        }
        return temp;
    }

    /**
     * Gets ApiSession object from session
     * @param request
     * @return
     */
    public static ApiSession getApiSession(HttpServletRequest request)
    {
        return (ApiSession) request.getSession().getAttribute(getSessionKey(request, Constants.SessionKeys.ApiSession));
    }

    /**
     * Gets ApiSessionId from session
     * @param request
     * @return
     */
    public static String GetApiSessionId(HttpServletRequest request)
    {
        return (String) request.getSession().getAttribute(getSessionKey(request, Constants.SessionKeys.ApiSessionId));
    }

    /**
     * Gets permissions for LearningObjectInstance from session
     * @param request
     * @return
     */
    public static LearningObjectInstancePermissions getPermissions(HttpServletRequest request)
    {
        return (LearningObjectInstancePermissions) request.getSession().getAttribute(Constants.SessionKeys.Permissions);
    }

    /**
     *
     * @param request
     * @return
     */
    public static UserInfo getUserInfo(HttpServletRequest request)
    {
        return (UserInfo) request.getSession().getAttribute(Constants.SessionKeys.UserInfo);
    }

    static void validateQueryString(String queryString, String sharedSecret, int requestLifetimeInMinutes,
            ViewLearningToolRequestParams parameters) throws UnsupportedEncodingException
    {
        if (queryString == null || queryString.isEmpty())
        {
            throw new RuntimeException("queryString is empty");
        }
        if (sharedSecret == null || sharedSecret.isEmpty())
        {
            throw new RuntimeException("sharedSecret is empty");
        }
        if (parameters == null)
        {
            throw new RuntimeException("parameters are empty");
        }

        // Check if timestamp is correct and within acceptable range
        if (parameters.getTimestamp() == null || parameters.getTimestamp().isEmpty())
        {
            throw new RuntimeException("Timestamp is not specified.");
        }

        Calendar resultdate = new GregorianCalendar();
        try
        {
            resultdate.setTimeInMillis(sdf.parse(parameters.getTimestamp()).getTime());
        } catch (ParseException ex)
        {
            throw new RuntimeException("Query string has an invalid timestamp (check that UTC time is passed and that server time is correct)");
        }

        long requestLifeTimeInMilliSeconds = requestLifetimeInMinutes * 60 * 1000;

        // TODO: possible to do the now and tooLate in a better way?
        Calendar tooEarly = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        tooEarly.setTimeInMillis(tooEarly.getTimeInMillis() - requestLifeTimeInMilliSeconds);
        Calendar tooLate = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        tooLate.setTimeInMillis(tooLate.getTimeInMillis() + requestLifeTimeInMilliSeconds);

        // Check if resultDate is withing limits of requestLifeTime
        if (resultdate.after(tooEarly) && resultdate.before(tooLate))
        {
            throw new RuntimeException("Query string has an invalid timestamp (check that UTC time is passed and that server time is correct)");
        }

        // Check signature
        if (parameters.getSignature() == null || parameters.getSignature().isEmpty())
        {
            throw new RuntimeException("Signature is not specified.");
        }
        try
        {
            // Remove signature from the querystring
            queryString = URLDecoder.decode(queryString, "UTF-8");
        } catch (UnsupportedEncodingException ex)
        {
            throw ex;
        }
        String queryStringWithoutSignature = queryString.replaceFirst(String.format("&Signature=%s", parameters.getSignature()), "");

        if (!CryptographyHelper.computeHash(queryStringWithoutSignature + sharedSecret).equals(parameters.getSignature()))
        {
            throw new RuntimeException("Signature is invalid.");
        }
    }

    private static void storeParametersToSession(ViewLearningToolRequestParams parameters, HttpSession session,
            String applicationKey, String sharedSecret)
    {
        // Get data from parameters, validate types
        if (parameters.getApiSessionId() == null)
        {
            throw new RuntimeException("ApiSessionId is not specified in the querystring.");
        }
        if (parameters.getLearningObjectId() == null)
        {
            throw new RuntimeException("LearningObjectId is not specified in the querystring.");
        }
        if (parameters.getLearningObjectInstanceId() == null)
        {
            throw new RuntimeException("LearningObjectInstanceId is not specified in the querystring.");
        }
        if (parameters.getVersion() == null)
        {
            throw new RuntimeException("Version is not specified in the querystring.");
        }
        if (parameters.getPermissions() == null)
        {
            throw new RuntimeException("Permissions is not specified in the querystring.");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setAccessibility(parameters.getAccessibility() != null ? parameters.getAccessibility() : false);
        userInfo.setFirstName(parameters.getFirstName());
        userInfo.setLastName(parameters.getLastName());
        userInfo.setLanguage(parameters.getLanguage());
        userInfo.setLocale(parameters.getLocale());
        userInfo.setWindowsTimeZoneId(parameters.getWindowsTimeZoneId());
        userInfo.setOlsonTimeZoneId(parameters.getOlsonTimeZoneId());
        userInfo.setUser12HTimeFormat(parameters.getUse12HTimeFormat() != null ? parameters.getUse12HTimeFormat() : false);
        userInfo.setUserId(parameters.getUserId());

        ApiSession apiSession = constuctApiSession(parameters.getApiSessionId(), applicationKey, sharedSecret);

        // Put data into session
        // API session and permissions vary for different documents within the same ASP.NET application session
        session.setAttribute(getSessionKey(parameters, Constants.SessionKeys.ApiSessionId), parameters.getApiSessionId());
        session.setAttribute(getSessionKey(parameters, Constants.SessionKeys.ApiSession), apiSession);
        session.setAttribute(getSessionKey(parameters, Constants.SessionKeys.Permissions), parameters.getPermissions());

        // UserInfo info is the same for all documents within the same ASP.NET application session
        session.setAttribute(Constants.SessionKeys.UserInfo, userInfo);

    }

    private static ApiSession constuctApiSession(String apiSessionId, String applicationKey, String sharedSecret)
    {
        if (apiSessionId == null || apiSessionId.isEmpty())
        {
            throw new IllegalArgumentException("apiSessionId was empty or null");
        }
        if (applicationKey == null || applicationKey.isEmpty())
        {
            throw new IllegalArgumentException("applicationKey was empty or null");
        }
        if (sharedSecret == null || sharedSecret.isEmpty())
        {
            throw new IllegalArgumentException("sharedSecret was empty or null");
        }

        ApiSession apiSession = ApiSession.createApiSession(apiSessionId);
        apiSession.setApplicationKey(applicationKey);
        apiSession.setHash(CryptographyHelper.computeHash(apiSession, sharedSecret));

        return apiSession;
    }

    private static String getSessionKey(HttpServletRequest request, String key)
    {
        return getSessionKey(getLearningObjectId(request), getLearningObjectInstanceId(request), key);
    }

    private static String getSessionKey(ViewLearningToolRequestParams parameters, String key)
    {
        return getSessionKey(parameters.getLearningObjectId().intValue(), parameters.getLearningObjectInstanceId().intValue(), key);
    }

    private static String getSessionKey(int learningObjectId, int learningObjectInstanceId, String key)
    {
        return String.format("%d;%d;%s", learningObjectId, learningObjectInstanceId, key);
    }

    private static ViewLearningToolRequestParams getParams(Map requestParameterMap)
    {
        return (ViewLearningToolRequestParams) RequestParamsHandler.getParams(requestParameterMap, ViewLearningToolRequestParams.class);
    }
}
