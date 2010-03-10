package itslearning.platform.restApi.sdk.common.entities;

import itslearning.platform.restApi.sdk.common.entities.Constants.UserRole;
import java.io.Serializable;
import java.util.*;

/**
 * UserInfo entity for RestApi clients.
 * @author Amund Trovåg
 */
public class UserInfo implements Serializable
{
    private int _userId;
    private String _windowsTimeZoneId;
    private String _olsonTimeZoneId;
    private String _firstName;
    private String _lastName;
    private String _locale;
    private String _language;
    private boolean _user12HTimeFormat;
    private boolean _accessibility;
    private int _customerId;
    private ArrayList<SchoolInfo> _schools;
    private Constants.UserRole _userRole;

    /**
     *
     * @return Unique ID for a site/customer
     */
    public int getCustomerId() {
	return _customerId;
    }

    /**
     * Set the unique ID for a site/customer
     */
    public void setCustomerId(int customerId) {
	this._customerId = customerId;
    }

    /**
     *
     * @return list of schools
     */
    public ArrayList<SchoolInfo> getSchools() {
	return _schools;
    }

    /**
     *
     * @param schools the schools the user belongs to.
     */
    public void setSchools(ArrayList<SchoolInfo> schools){
	_schools = schools;
    }

 
    /**
     * @return the _userId
     */
    public int getUserId()
    {
        return _userId;
    }

    /**
     * @param userId the _userId to set
     */
    public void setUserId(int userId)
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
     * Defines whether to use 12 or 24 hours time format.
     * @return the _user12HTimeFormat
     */
    public boolean isUser12HTimeFormat()
    {
        return _user12HTimeFormat;
    }

    /**
     * Defines whether to use 12 or 24 hours time format.
     * @param user12HTimeFormat the _user12HTimeFormat to set
     */
    public void setUser12HTimeFormat(boolean user12HTimeFormat)
    {
        this._user12HTimeFormat = user12HTimeFormat;
    }

    /**
     * @return the _accessibility
     */
    public boolean isAccessibility()
    {
        return _accessibility;
    }

    /**
     * @param accessibility the _accessibility to set
     */
    public void setAccessibility(boolean accessibility)
    {
        this._accessibility = accessibility;
    }

    /**
     * Gets the_userRole
     * @return
     */
    public UserRole getUserRole()
    {
        return _userRole;
    }

    /**
     *
     * @param userRole the serRole to set
     */
    public void setUserRole(UserRole userRole)
    {
        this._userRole = userRole;
    }

}
