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
    private String _email;
    private String _syncKey;
    private String _mobile;
    private String _locale;
    private String _language;
    private boolean _user12HTimeFormat;
    private boolean _accessibility;
    private String _encoding;
    private String _custom1id;
    private String _custom2id;
    private String _custom3id;
    private String _custom4id;
    private String _custom5id;
    private String _custom1;
    private String _custom2;
    private String _custom3;
    private String _custom4;
    private String _custom5;
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
     * @return the _email
     */
    public String getEmail()
    {
        return _email;
    }

    /**
     * @param email the _email to set
     */
    public void setEmail(String email)
    {
        this._email = email;
    }

     /**
     * @return the _syncKey
     */
    public String getSyncKey()
    {
        return _syncKey;
    }

    /**
     * @param syncKey the _syncKey to set
     */
    public void setSyncKey(String syncKey)
    {
        this._syncKey = syncKey;
    }
    
     /**
     * @return the _mobile
     */
    public String getMobile()
    {
        return _mobile;
    }

    /**
     * @param mobile the _mobile to set
     */
    public void setMobile(String mobile)
    {
        this._mobile = mobile;
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
     * @return the _custom1id
     */
    public String getCustom1Id()
    {
        return _custom1id;
    }

    /**
     * @param custom1Id the _custom1id to set
     */
    public void setCustom1Id(String custom1Id)
    {
        this._custom1id = custom1Id;
    }
    
    /**
     * @return the _custom2id
     */
    public String getCustom2Id()
    {
        return _custom2id;
    }

    /**
     * @param custom2Id the _custom2id to set
     */
    public void setCustom2Id(String custom2Id)
    {
        this._custom2id = custom2Id;
    }
    
    /**
     * @return the _custom3id
     */
    public String getCustom3Id()
    {
        return _custom3id;
    }

    /**
     * @param custom3Id the _custom3id to set
     */
    public void setCustom3Id(String custom3Id)
    {
        this._custom3id = custom3Id;
    }
    
    /**
     * @return the _custom4id
     */
    public String getCustom4Id()
    {
        return _custom4id;
    }

    /**
     * @param custom4Id the _custom4id to set
     */
    public void setCustom4Id(String custom4Id)
    {
        this._custom4id = custom4Id;
    }
    
    /**
     * @return the _custom5id
     */
    public String getCustom5Id()
    {
        return _custom5id;
    }

    /**
     * @param custom5Id the _custom5id to set
     */
    public void setCustom5Id(String custom5Id)
    {
        this._custom5id = custom5Id;
    }
   
   /**
     * @return the _custom1
     */
    public String getCustom1()
    {
        return _custom1;
    } 
    
   /**
     * @param custom1 the _custom1 to set
     */
    public void setCustom1(String custom1)
    {
        this._custom1 = custom1;
    }
    
    /**
     * @return the _custom2
     */
    public String getCustom2()
    {
        return _custom2;
    }

    /**
     * @param custom2 the _custom2 to set
     */
    public void setCustom2(String custom2)
    {
        this._custom2 = custom2;
    }
    
    /**
     * @return the _custom3
     */
    public String getCustom3()
    {
        return _custom3;
    }

    /**
     * @param custom3 the _custom3 to set
     */
    public void setCustom3(String custom3)
    {
        this._custom3 = custom3;
    }
    
    /**
     * @return the _custom4
     */
    public String getCustom4()
    {
        return _custom4;
    }

    /**
     * @param custom4 the _custom4 to set
     */
    public void setCustom4(String custom4)
    {
        this._custom4 = custom4;
    }
    
    /**
     * @return the _custom5
     */
    public String getCustom5()
    {
        return _custom5;
    }

    /**
     * @param custom5 the _custom5 to set
     */
    public void setCustom5(String custom5)
    {
        this._custom5 = custom5;
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
    
    /*
     * Get the encoding used to interpret the query string when signing it.
     */
    public String getEncoding()
    {
        return _encoding;
    }
    
    /*
     * Set the encoding used to interpret the query string when signing it.
     */
    public void setEncoding(String encoding)
    {
        this._encoding = encoding;
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
