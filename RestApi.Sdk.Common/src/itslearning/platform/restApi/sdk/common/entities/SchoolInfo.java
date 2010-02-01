package itslearning.platform.restApi.sdk.common.entities;

import java.io.Serializable;

/**
 * Contains information about a school.
 * Presently the unique it's learning school ID and the school's legal ID.
 * @author Kristian F. Schmidt
 */
public class SchoolInfo implements Serializable {

    private int _schoolId;
    private String _legalId;

    public String getLegalId() {
	return _legalId;
    }

    public void setLegalId(String legalId) {
	this._legalId = legalId;
    }

    public int getSchoolId() {
	return _schoolId;
    }

    public void setSchoolId(int schoolId) {
	this._schoolId = schoolId;
    }

    public boolean isValid(){
	return _schoolId > 0;
    }

    @Override
    public boolean equals(Object obj) {
	if( ! (obj instanceof SchoolInfo))
	    return false;

	SchoolInfo target = (SchoolInfo)obj;

	return hashCode() == target.hashCode();
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 41 * hash + this._schoolId;
	hash = 41 * hash + (this._legalId != null ? this._legalId.hashCode() : 0);
	return hash;
    }




    @Override
    public String toString(){
	return "("+_schoolId+"|"+(_legalId == null ? "null" : _legalId)+")";
    }

}
