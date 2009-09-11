package itslearning.platform.restApi.sdk.common;

import org.springframework.http.*;
/**
 *
 * @author Amund
 */
public class HttpStatusWrapper
{

    private HttpStatus _statusCode;
    private String _description;

    /// <exclude/>
    public HttpStatusWrapper(HttpStatus statusCode, String description)
    {
        _statusCode = statusCode;
        _description = description;
    }

        /// <exclude/>
    public HttpStatusWrapper(int statusCode)
    {
        _statusCode = HttpStatus.valueOf(statusCode);
        _description = _statusCode.name();
    }

    /// <exclude/>
    public HttpStatusWrapper(HttpStatus statusCode)
    {
        _statusCode = statusCode;
        _description = _statusCode.name();
    }

    /**
     * @return the _statusCode
     */
    public HttpStatus getStatusCode()
    {
        return _statusCode;
    }

    /**
     * @param statusCode the _statusCode to set
     */
    public void setStatusCode(HttpStatus statusCode)
    {
        this._statusCode = statusCode;
        _description = _statusCode.name();
    }

    /**
     * @return the _description
     */
    public String getDescription()
    {
        return _description;
    }

}
