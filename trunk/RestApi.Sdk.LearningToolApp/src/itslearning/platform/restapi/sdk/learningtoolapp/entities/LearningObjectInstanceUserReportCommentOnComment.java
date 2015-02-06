package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import java.util.Date;

/**
 * Entity for for RestApi clients, representing a comment for learning object instance user report comment.
 * @author Yaroslav Kulik
 */
public class LearningObjectInstanceUserReportCommentOnComment {

    private Integer _userId;
    private String _commentText;
    private String _commentSyncKey;
    private Date _modifiedUtc;
    
    /**
     * @return User id.
     */
    public Integer getUserId()
    {
        return _userId;
    }

    /**
     * @param userId User id.
     */
    public void setUserId(Integer userId)
    {
        _userId = userId;
    }
    
    /**
     * @return Comment text.
     */
    public String getCommentText()
    {
        return _commentText;
    }

    /**
     * @param commentText Comment text.
     */
    public void setCommentText(String commentText)
    {
        _commentText = commentText;
    }
    
    /**
     * @return Comment synchronization key.
     */
    public String getCommentSyncKey()
    {
        return _commentSyncKey;
    }

    /**
     * @param commentSyncKey Comment synchronization key.
     */
    public void setCommentSyncKey(String commentSyncKey)
    {
        _commentSyncKey = commentSyncKey;
    }
    
    /**
     * @return Date of comment modification in UTC.
     */
    public Date getModifiedUtc()
    {
        return _modifiedUtc;
    }

    /**
     * @param modifiedUtc Date of comment modification in UTC.
     */
    public void setModifiedUtc(Date modifiedUtc)
    {
        _modifiedUtc = modifiedUtc;
    }
}
