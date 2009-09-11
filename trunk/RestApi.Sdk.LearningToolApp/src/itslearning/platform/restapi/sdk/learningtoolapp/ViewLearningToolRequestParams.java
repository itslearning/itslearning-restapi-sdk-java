package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.IRequestParams;
import itslearning.platform.restApi.sdk.common.entities.Constants.*;
import itslearning.platform.restApi.sdk.common.entities.LearningObjectInstancePermissions;

/**
 * Represents request params that is passed from it's learning to the learning application when called from it's learning.
 * @author Amund Trovåg
 */
public class ViewLearningToolRequestParams implements IRequestParams
{
    private String _pageUrl;
    private String _apiSessionId;
    private Integer _learningObjectId;
    private Integer _learningObjectInstanceId;
    private String _version;
    private Integer _userId;
    private String _windowsTimeZoneId;
    private String _olsonTimeZoneId;
    private String _firstName;
    private String _lastName;
    private String _locale;
    private String _language;
    private Boolean _use12HTimeFormat;
    private Boolean _accessibility;
    private LearningObjectInstancePermissions _permissions = new LearningObjectInstancePermissions();
    private String _timestamp;
    private String _signature;

    public String getPageUrl()
    {
        return _pageUrl;
    }

    /**
     * @param pageUrl the _pageUrl to set
     */
    public void setPageUrl(String pageUrl)
    {
        this._pageUrl = pageUrl;
    }

    /**
     * @return the _apiSessionId
     */
    public String getApiSessionId()
    {
        return _apiSessionId;
    }

    /**
     * @param apiSessionId the _apiSessionId to set
     */
    public void setApiSessionId(String apiSessionId)
    {
        this._apiSessionId = apiSessionId;
    }

    /**
     * @return the _learningObjectId
     */
    public Integer getLearningObjectId()
    {
        return _learningObjectId;
    }

    /**
     * @param learningObjectId the _learningObjectId to set
     */
    public void setLearningObjectId(Integer learningObjectId)
    {
        this._learningObjectId = learningObjectId;
    }

    /**
     * @return the _learningObjectInstanceId
     */
    public Integer getLearningObjectInstanceId()
    {
        return _learningObjectInstanceId;
    }

    /**
     * @param learningObjectInstanceId the _learningObjectInstanceId to set
     */
    public void setLearningObjectInstanceId(Integer learningObjectInstanceId)
    {
        this._learningObjectInstanceId = learningObjectInstanceId;
    }

    /**
     * @return the _version
     */
    public String getVersion()
    {
        return _version;
    }

    /**
     * @param version the _version to set
     */
    public void setVersion(String version)
    {
        this._version = version;
    }

    /**
     * @return the _userId
     */
    public Integer getUserId()
    {
        return _userId;
    }

    /**
     * @param userId the _userId to set
     */
    public void setUserId(Integer userId)
    {
        this._userId = userId;
    }

    /**
     * @return the _windowsTimeZoneId
     */
    public String getWindowsTimeZoneId()
    {
        return _windowsTimeZoneId;
    }

    /**
     * @param windowsTimeZoneId the _windowsTimeZoneId to set
     */
    public void setWindowsTimeZoneId(String windowsTimeZoneId)
    {
        this._windowsTimeZoneId = windowsTimeZoneId;
    }

    /**
     * @return the _olsonTimeZoneId
     */
    public String getOlsonTimeZoneId()
    {
        return _olsonTimeZoneId;
    }

    /**
     * @param olsonTimeZoneId the _olsonTimeZoneId to set
     */
    public void setOlsonTimeZoneId(String olsonTimeZoneId)
    {
        this._olsonTimeZoneId = olsonTimeZoneId;
    }

    /**
     * @return the _firstName
     */
    public String getFirstName()
    {
        return _firstName;
    }

    /**
     * @param firstName the _firstName to set
     */
    public void setFirstName(String firstName)
    {
        this._firstName = firstName;
    }

    /**
     * @return the _lastName
     */
    public String getLastName()
    {
        return _lastName;
    }

    /**
     * @param lastName the _lastName to set
     */
    public void setLastName(String lastName)
    {
        this._lastName = lastName;
    }

    /**
     * @return the _locale
     */
    public String getLocale()
    {
        return _locale;
    }

    /**
     * @param locale the _locale to set
     */
    public void setLocale(String locale)
    {
        this._locale = locale;
    }

    /**
     * @return the _language
     */
    public String getLanguage()
    {
        return _language;
    }

    /**
     * @param language the _language to set
     */
    public void setLanguage(String language)
    {
        this._language = language;
    }

    /**
     * @return the _use12HTimeFormat
     */
    public Boolean getUse12HTimeFormat()
    {
        return _use12HTimeFormat;
    }

    /**
     * @param use12HTimeFormat the _use12HTimeFormat to set
     */
    public void setUse12HTimeFormat(Boolean use12HTimeFormat)
    {
        this._use12HTimeFormat = use12HTimeFormat;
    }

    /**
     * @return the _accessibility
     */
    public Boolean getAccessibility()
    {
        return _accessibility;
    }

    /**
     * @param accessibility the _accessibility to set
     */
    public void setAccessibility(Boolean accessibility)
    {
        this._accessibility = accessibility;
    }

    /**
     * @return the _permissions
     */
    public LearningObjectInstancePermissions getPermissions()
    {
        return _permissions;
    }

    /**
     * @param permissions the _permissions to set
     */
    public void setPermissions(String permissions)
    {
        permissions = permissions.toUpperCase();
        String[] permissionsSplit = permissions.split(",");
        _permissions.addPermission(permissionsSplit);
    }

    /**
     * @return the _timestamp
     */
    public String getTimestamp()
    {
        return _timestamp;
    }

    /**
     * @param timestamp the _timestamp to set
     */
    public void setTimestamp(String timestamp)
    {
        this._timestamp = timestamp;
    }

    /**
     * @return the _signature
     */
    public String getSignature()
    {
        return _signature;
    }

    /**
     * @param signature the _signature to set
     */
    public void setSignature(String signature)
    {
        this._signature = signature;
    }
}
