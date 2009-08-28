/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class represents API session.
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class ApiSession implements Serializable
{

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private String _applicationKey;
    private String _hash;
    private String _sessionId;
    private Date _lastRequestDateTimeUtc;

    public ApiSession()
    {
    }

    public ApiSession(String sessionId)
    {
        _sessionId = sessionId;
        // TODO: is it correct to set it here? It's set here to prevent getTimeStamp() to fail in some cases where this is null
        _lastRequestDateTimeUtc = new Date();
    }

    public String getTimeStamp()
    {
        return sdf.format(getLastRequestDateTimeUtc());
    }

    public static Date parseTimeStamp(String str)
    {
        try
        {
            return sdf.parse(str);
        } catch (ParseException ex)
        {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Creates a new instance of the class with specified Id.
     * @param sessionId
     * @return New instance
     */
    public static ApiSession createApiSession(String sessionId)
    {
        if (sessionId == null || sessionId.isEmpty())
        {
            return null;
        }

        return new ApiSession(sessionId);
    }

    /**
     * @return the _applicationKey
     */
    public String getApplicationKey()
    {
        return _applicationKey;
    }

    /**
     * @param applicationKey the _applicationKey to set
     */
    public void setApplicationKey(String applicationKey)
    {
        this._applicationKey = applicationKey;
    }

    /**
     * @return the _hash
     */
    public String getHash()
    {
        return _hash;
    }

    /**
     * @param hash the _hash to set
     */
    public void setHash(String hash)
    {
        this._hash = hash;
    }

    /**
     * @return the _sessionId
     */
    public String getSessionId()
    {
        return _sessionId;
    }

    /**
     * @param sessionId the _sessionId to set
     */
    public void setSessionId(String sessionId)
    {
        this._sessionId = sessionId;
    }

    /**
     * @return the _lastRequestDateTimeUtc
     */
    public Date getLastRequestDateTimeUtc()
    {
        return _lastRequestDateTimeUtc;
    }

    /**
     * @param lastRequestDateTimeUtc the _lastRequestDateTimeUtc to set
     */
    public void setLastRequestDateTimeUtc(Date lastRequestDateTimeUtc)
    {
        this._lastRequestDateTimeUtc = lastRequestDateTimeUtc;
    }
}
