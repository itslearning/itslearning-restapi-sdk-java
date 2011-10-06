package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import itslearning.platform.restApi.sdk.common.entities.Constants.EducationSegment;

/**
 * Site entity
 * 
 * @author mikael
 */
public class Site {
    
    /**
     * Education segment of the site
     */
    private EducationSegment _segment;
    
    /**
     * Country code of the site. ISO 3166 2-alpha. e.g. "NO" for Norway
     */
    private String _countryCode;
    
    /**
     * @return the _segment
     */
    public EducationSegment getSegment() {
        return _segment;
    }

    /**
     * @param type the _segment to set
     */
    public void setSegment(EducationSegment segment) {
        this._segment = segment;
    }
    
   /**
     * @return the _countryCode
     */
    public String getCountryCode() {
        return _countryCode;
    }

    /**
     * @param title the _countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this._countryCode = countryCode;
    }
}
