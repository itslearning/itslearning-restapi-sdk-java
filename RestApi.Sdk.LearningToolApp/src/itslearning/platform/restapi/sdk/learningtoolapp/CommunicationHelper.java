package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.RequestParamsHandler;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import javax.servlet.http.*;
import itslearning.platform.restApi.sdk.common.Settings.IApplicationSettings;
import itslearning.platform.restApi.sdk.common.ThreadSafeDateFormat;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restApi.sdk.common.entities.Constants.UserRole;
import itslearning.platform.restApi.sdk.common.entities.LearningObjectInstancePermissions;
import itslearning.platform.restApi.sdk.common.entities.SchoolInfo;
import itslearning.platform.restApi.sdk.common.entities.UserInfo;
import java.net.URLDecoder;
import java.util.ArrayList;
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

    private static ThreadSafeDateFormat sdf = new ThreadSafeDateFormat("yyyy-MM-dd'T'HH:mm:ss");

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
     * Alternative method for initializing apisession if you need to modify the baseUrl of the HttpServletRequest
     * @param request
     * @param modifiedBaseUrl
     * @param settings
     */
    public static void initApiSession(HttpServletRequest request, String modifiedBaseUrl, IApplicationSettings settings)
    {
        ViewLearningToolRequestParams parameters = getParams(request.getParameterMap());
        try
        {
            // Validate if the request is not expired and if the signature is valid
            validateQueryString(String.format("%s?%s", modifiedBaseUrl,request.getQueryString()), settings.getSharedSecret(), settings.getRequestLifetimeInMinutes(), parameters);
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
        return (LearningObjectInstancePermissions) request.getSession().getAttribute(getSessionKey(request, Constants.SessionKeys.Permissions));
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

    protected static void validateQueryString(String queryString, String sharedSecret, int requestLifetimeInMinutes,
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
        // Check signature in order to see that the URL has not been tampered with
        String queryStringWithoutSignature = queryString.replaceFirst(String.format("&Signature=%s", parameters.getSignature()), "");

        if (!CryptographyHelper.computeHash(queryStringWithoutSignature + sharedSecret).equals(parameters.getSignature()))
        {
            throw new RuntimeException("Signature is invalid.");
        }
    }

    protected static void storeParametersToSession(ViewLearningToolRequestParams parameters, HttpSession session,
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
        if ( parameters.getCustomerId() != null )
            userInfo.setCustomerId(parameters.getCustomerId());

        // Default to guest if userRole is not sent (for backwardscompatibility)
        UserRole userRole = UserRole.Guest;
        if(parameters.getRole()!=null)
        {
            // Set the userRole that we get from it's learning parsed to enum
            userRole = UserRole.valueOf(parameters.getRole());
        }
        
        userInfo.setUserRole(userRole);

        try
        {
            userInfo.setSchools(buildSchoolInfoList(parameters.getSchoolId()));
        }

        catch(ParseException pe)
        {
            // Invalid format on the request parameter value 'SchoolId'.
            throw new RuntimeException("Invalid format on the request parameter value 'SchoolId': "+parameters.getSchoolId(), pe);
        }
	

        ApiSession apiSession = constructApiSession(parameters.getApiSessionId(), applicationKey, sharedSecret);

        // Put data into session
        // API session and permissions vary for different documents within the same ASP.NET application session
        session.setAttribute(getSessionKey(parameters, Constants.SessionKeys.ApiSessionId), parameters.getApiSessionId());
        session.setAttribute(getSessionKey(parameters, Constants.SessionKeys.ApiSession), apiSession);
        session.setAttribute(getSessionKey(parameters, Constants.SessionKeys.Permissions), parameters.getPermissions());

        // UserInfo info is the same for all documents within the same ASP.NET application session
        session.setAttribute(Constants.SessionKeys.UserInfo, userInfo);

    }

    private static ApiSession constructApiSession(String apiSessionId, String applicationKey, String sharedSecret)
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

    /**
     * Parses the SchoolId request string and builds a list of SchoolInfo objects.
     * 
     * @param schoolIdParameter The string received from the request containing a comma separated list of 'schoolId'|'legalId' pairs.
     * @return
     * @throws ParseException if we encounter a non-numeric schoolId.
     */
    protected static ArrayList<SchoolInfo> buildSchoolInfoList(String schoolIdParameter) throws ParseException
    {
        ArrayList<SchoolInfo> schools = new ArrayList<SchoolInfo>();

        if( schoolIdParameter == null || schoolIdParameter.length() == 0)
            return schools;

        char[] schoolIds = schoolIdParameter.toCharArray();
        boolean escaped = false;
        StringBuffer buffer = new StringBuffer();
        SchoolInfo schoolInfo = new SchoolInfo();

        for( int i=0; i < schoolIds.length; ++i ){
            if( !escaped && schoolIds[i] == '\\' ){
                escaped = true;
                continue; //Skip the escape character
            }

            if( escaped ){
            escaped = false;
            buffer.append(schoolIds[i]);
            }
            else if( schoolIds[i] == '|' ){
            // set the school Id in the schoolInfo object.
            String schoolId = buffer.toString().trim();

            try {
                schoolInfo.setSchoolId( Integer.parseInt(schoolId) );
            }
            catch (NumberFormatException numberFormatException) {
                throw new ParseException("Could not parse school ID parameter ('"+schoolId+"'). Error exist between character "+ (i-buffer.length())+" and "+i , i-buffer.length() );
            }
            // reset buffer
            buffer.delete(0, buffer.length());
            }
            else if( schoolIds[i] == ','){
            // Set the legal Id in the schoolInfo object, if available.


            if( buffer.length() > 0){

                schoolInfo.setLegalId(buffer.toString().trim());
                // reset buffer
                buffer.delete(0, buffer.length());
            }

            // Add the schoolInfo object to the list of schools.
            schools.add(schoolInfo);
            schoolInfo = new SchoolInfo();
            }
            else{
            buffer.append(schoolIds[i]);
            }

        }
        if( buffer.length() > 0 ){
            schoolInfo.setLegalId(buffer.toString().trim());
            buffer = null;
        }
        if( schoolInfo.isValid())
            schools.add(schoolInfo);

        return schools;
    }

    protected static ViewLearningToolRequestParams getParams(Map requestParameterMap)
    {
        return RequestParamsHandler.getParams(requestParameterMap, ViewLearningToolRequestParams.class);
    }


}
