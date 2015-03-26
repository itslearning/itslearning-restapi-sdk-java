/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 *
 * @author oleg.tarusov
 */
public class CollaborationParticipant {
    private int userId;
    private String firstName;
    private String lastName;
    private int collaborationId;    
    
    /**
     * @return the userId
     */
    public int getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    /**
     * @return CollaborationId
     */
    public int getCollaborationId()
    {
        return collaborationId;
    }

    /**
     * @param collaborationId the collaborationId to set.
     */
    public void setCollaborationId(int collaborationId)
    {
        this.collaborationId = collaborationId;
    }        
}
