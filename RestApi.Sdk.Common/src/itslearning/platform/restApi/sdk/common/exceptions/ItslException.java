package itslearning.platform.restApi.sdk.common.exceptions;

import java.io.Serializable;

/**
 * Business logic exception.
 * @author Amund Trovåg
 */
public class ItslException extends Exception implements Serializable
{

    private ItslExceptionCode _code;

    public ItslException(ItslExceptionCode code)
    {
        _code = code;
    }

    public ItslException(ItslExceptionCode code, String message)
    {
        super(message);
        _code = code;
    }

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
}
