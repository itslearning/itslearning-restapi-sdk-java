package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Represents a license for your learning tool application
 * @author amund
 */
public class AppLicense
{
    /**
     * Itslearning's internal identifier for the license
     */
    private Integer _licenseId;
    /**
     * The external identifier for the license
     */
    private String _externalLicenseId;

    /**
     * @return the _licenseId
     */
    public Integer getLicenseId() {
        return _licenseId;
    }

    /**
     * @param licenseId the _licenseId to set
     */
    public void setLicenseId(Integer licenseId) {
        this._licenseId = licenseId;
    }

    /**
     * @return the _externalLicenseId
     */
    public String getExternalLicenseId() {
        return _externalLicenseId;
    }

    /**
     * @param externalLicenseId the _externalLicenseId to set
     */
    public void setExternalLicenseId(String externalLicenseId) {
        this._externalLicenseId = externalLicenseId;
    }

}
