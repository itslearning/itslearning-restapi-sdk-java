/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

/**
 *
 * @author Amund
 */
class HttpStatus
{

    private org.springframework.http.HttpStatus _statusCode;
    private String _description;

    /// <exclude/>
    public HttpStatus(org.springframework.http.HttpStatus statusCode, String description)
    {
        _statusCode = statusCode;
        _description = description;
    }

    /// <exclude/>
    public HttpStatus(org.springframework.http.HttpStatus statusCode)
    {
        _statusCode = statusCode;
        _description = _statusCode.toString();
    }

    /**
     * @return the _statusCode
     */
    public org.springframework.http.HttpStatus getStatusCode()
    {
        return _statusCode;
    }

    /**
     * @param statusCode the _statusCode to set
     */
    public void setStatusCode(org.springframework.http.HttpStatus statusCode)
    {
        this._statusCode = statusCode;
        _description = _statusCode.toString();
    }

    /**
     * @return the _description
     */
    public String getDescription()
    {
        return _description;
    }

}
