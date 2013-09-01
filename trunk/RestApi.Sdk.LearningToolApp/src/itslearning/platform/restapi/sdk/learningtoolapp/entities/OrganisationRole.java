package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Represents user's role in organization
 * @author piotr
 */
public class OrganisationRole {

    /**
     * Internal identifier for an organisation in itslearning. Is unique in combination with customerId.
     */
    private Integer _hierarchyId;
    
    /**
     * Determines whether it is a role in user's home organization.
     */
    private Boolean _homeOrganization;
    
    /**
     * The name of the role.
     */
    private String _role;   

    /**
     * @return the _hierarchyId
     */
    public Integer getHierarchyId() {
        return _hierarchyId;
    }

    /**
     * @param hierarchyId the _hierarchyId to set
     */
    public void setHierarchyId(Integer hierarchyId) {
        this._hierarchyId = hierarchyId;
    }

    /**
     * @return the _homeOrganization
     */
    public Boolean getHomeOrganization() {
        return _homeOrganization;
    }

    /**
     * @param homeOrganization the _homeOrganization to set
     */
    public void setHomeOrganization(Boolean homeOrganization) {
        this._homeOrganization = homeOrganization;
    }

    /**
     * @return the _role
     */
    public String getRole() {
        return _role;
    }

    /**
     * @param role the _role to set
     */
    public void setRole(String role) {
        this._role = role;
    }
}