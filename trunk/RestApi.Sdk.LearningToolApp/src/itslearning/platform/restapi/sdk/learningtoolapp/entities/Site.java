package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import itslearning.platform.restApi.sdk.common.entities.Constants.EducationSegment;

/**
 * Site entity
 * 
 * @author mikael
 */
public class Site {
    
    private EducationSegment _segment;
    private String _countryCode;
    private String _name;
    private String _shortName;
    private String _baseUrl;
    
    /**
     * @return Education segment of the site.
     */
    public EducationSegment getSegment() {
        return _segment;
    }

    /**
     * @param segment Education segment of the site.
     */
    public void setSegment(EducationSegment segment) {
        _segment = segment;
    }
    
    /**
     * @return Country code of the site. ISO 3166 2-alpha. e.g. "NO" for Norway
     */
    public String getCountryCode() {
        return _countryCode;
    }

    /**
     * @param countryCode Country code of the site. ISO 3166 2-alpha. e.g. "NO" for Norway
     */
    public void setCountryCode(String countryCode) {
        _countryCode = countryCode;
    }
    
    /**
     * @return The name of the site
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name The name of the site
     */
    public void setName(String name) {
        _name = name;
    }
    
    /**
     * @return The shortname of the site
     */
    public String getShortName() {
        return _shortName;
    }

    /**
     * @param shortName The shortname of the site
     */
    public void setShortName(String shortName) {
        _shortName = shortName;
    }
    
    /**
     * @return The base url of the site pages.
     */
    public String getBaseUrl() {
        return _baseUrl;
    }

    /**
     * @param baseUrl The base url of the site pages.
     */
    public void setBaseUrl(String baseUrl) {
        _baseUrl = baseUrl;
    }
}
