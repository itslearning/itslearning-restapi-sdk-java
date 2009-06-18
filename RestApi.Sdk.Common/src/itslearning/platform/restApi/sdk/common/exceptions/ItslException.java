/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common.exceptions;

/**
 *
 * @author Amund
 */
public class ItslException extends Exception
{

    private ItslExceptionCode _code;

    /// <exclude/>
    public ItslException(ItslExceptionCode code)
    {
        _code = code;
    }

    /// <exclude/>
    public ItslException(ItslExceptionCode code, String message)
    {
        super(message);
        _code = code;
    }

    /// <exclude/>
    public ItslException(ItslExceptionCode code, String message, Exception innerException)
    {
        super(message, innerException);
        _code = code;
    }

    /**
     * Overrides toString of Exception
     * @return
     */
    @Override
    public String toString()
    {
        return String.format("%s: %s", getCode(), super.toString());
    }

    /**
     * @return the _code
     */
    public ItslExceptionCode getCode()
    {
        return _code;
    }

    /**
     * @param code the _code to set
     */
    public void setCode(ItslExceptionCode code)
    {
        this._code = code;
    }
    /* TODO: missing implementation of the following
    /// <exclude/>
    public static implicit operator string(ItslException ex)
    {
    return ex.ToString();
    }

    /// <exclude/>
    [SecurityPermission(SecurityAction.LinkDemand, Flags = SecurityPermissionFlag.SerializationFormatter)]
    public override void GetObjectData(SerializationInfo info, StreamingContext context)
    {
    if (info == null)
    {
    throw new ArgumentNullException("info");
    }

    base.GetObjectData(info, context);
    }*/
}
