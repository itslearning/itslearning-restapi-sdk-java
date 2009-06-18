/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common.exceptions;

import java.io.Serializable;

/**
 * Exception thrown when referenced entity is not found or not accessible.
 * @author Amund Trovåg
 */
public class ObjectNotExistOrAccessDeniedException extends Exception implements Serializable
{

    private Class _referencedEntityType;
    private Integer _referencedEntityId;
    private String _message;

    public class ReferencedEntityType
    {
    }

    public ObjectNotExistOrAccessDeniedException()
    {
    }

    public ObjectNotExistOrAccessDeniedException(String message)
    {
        super(message);
    }

    public ObjectNotExistOrAccessDeniedException(String message, Exception innerException)
    {
        super(message, innerException);
    }

    public ObjectNotExistOrAccessDeniedException(Class referencedEntityType, int referencedEntityId)
    {
        _referencedEntityType = referencedEntityType;
        _referencedEntityId = referencedEntityId;
    }

    public ObjectNotExistOrAccessDeniedException(Class referencedEntityType, String message)
    {
        _referencedEntityType = referencedEntityType;
        _message = message;
    }

    /// <summary>
    /// Error message.
    /// </summary>
    /// <returns></returns>
    @Override
    public String toString()
    {
        if (_referencedEntityId != null)
        {
            return String.format("%s with id=%s was not found.\n%s", _referencedEntityType.getName(), _referencedEntityId.intValue(), super.getMessage());
        }

        return String.format("%s was not found. %s\n%s", _referencedEntityType.getName(), _message, super.getMessage());
    }

    // TODO: not implemented yet
    /*
     *         /// <exclude/>
    public static implicit operator string(ObjectNotExistOrAccessDeniedException ex)
    {
    return ex.ToString();
    }
     *         /// <exclude/>
    [SecurityPermission(SecurityAction.LinkDemand, Flags = SecurityPermissionFlag.SerializationFormatter)]
    public override void GetObjectData(SerializationInfo info, StreamingContext context)
    {
    if (info == null)
    {
    throw new ArgumentNullException("info");
    }

    base.GetObjectData(info, context);
    }
     * */
}
