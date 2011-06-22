package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import itslearning.platform.restApi.sdk.common.entities.Constants.OrganisationType;

/**
 * Organisation represents school and site hierarchies in itslearning.
 * It's the structure that is used for organising the virtual learning environment and
 * mapping it to real-life schools
 * A site in itslearning is normally structured as
 * - Sitename (OrganisationType Site)
 *      - Schoolname1 (Organisationtype School)
 *          - Group
 *          - Group
 *          - ...
 *      - Schoolname2 (Organisationtype School)
 *          - ...
 * @author amund
 */
public class Organisation {

    /**
     * Internal identifier for an organisation in itslearning. Is unique in combination with customerId
     */
    private Integer _hierarchyId;
    /**
     * External identifier for an organisation in itslearning. Set by the administrators of the learning institution
     */
    private String _legalId;
    /**
     * The title of the organisation. Set by the administrators of the learning instituion
     */
    private String _title;
    /**
     * The type of organisation.
     */
    private OrganisationType _type;

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
     * @return the _legalId
     */
    public String getLegalId() {
        return _legalId;
    }

    /**
     * @param legalId the _legalId to set
     */
    public void setLegalId(String legalId) {
        this._legalId = legalId;
    }

    /**
     * @return the _title
     */
    public String getTitle() {
        return _title;
    }

    /**
     * @param title the _title to set
     */
    public void setTitle(String title) {
        this._title = title;
    }

    /**
     * @return the _type
     */
    public OrganisationType getType() {
        return _type;
    }

    /**
     * @param type the _type to set
     */
    public void setType(OrganisationType type) {
        this._type = type;
    }
}