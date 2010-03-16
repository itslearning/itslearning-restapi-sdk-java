/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Utility class to parse licenseIds from parameters to the application
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class LicenseHelper {

    /**
     * Takes two lists as strings of it's learning's and external license IDs and returns a dictionary containing
     * licenseId as key and externalLicenseId as value.
     * Escapes commas and backslashes in externalLicenseIdsStr (i.e. ',' to '\,' and
	 * /// '\' to '\\'.
     * @param licenseIds
     * @param externalLicenseIds
     */
    public static Dictionary<Integer, String> getLicenseIds(String licenseIds, String externalLicenseIds) throws IllegalArgumentException
    {
        if( (licenseIds == null || licenseIds.length()==0) || (externalLicenseIds == null || externalLicenseIds.length()==0) )
		{
			return new Hashtable<Integer, String>(0);
		}

        List<Integer> licenseIdsList = convertLicenseIdsCsv(licenseIds);
        List<String> externalLicenseIdsList = ConvertExternalLicenseCsv(externalLicenseIds);

        if (licenseIdsList.size() != externalLicenseIdsList.size())
        {
            // These must match!
            throw new IllegalArgumentException("Mismatch between size of licenseIds and externalLicenseIds.");
        }

        Hashtable<Integer, String> licenses = new Hashtable<Integer, String>(licenseIdsList.size());

        for(int i = 0; i < licenseIdsList.size(); i++){
            licenses.put(licenseIdsList.get(i), externalLicenseIdsList.get(i));
        }

        return licenses;
    }

    /**
     * Convert a string of comma separated escaped external license Ids to a list of unescaped external license Ids.
     * @param csv A string of comma separated escaped external license Ids.
     * @return A list of unescaped external license Ids
     */
    private static List<String> ConvertExternalLicenseCsv(String csv)
    {
        if(csv==null || csv.length()==0)
        {
            return new ArrayList<String>(0);
        }
        ArrayList<String> externalLicenseIds = new ArrayList<String>();
        State currentState = State.Normal;
        StringBuilder builder = new StringBuilder();

        Character previousCharacter = null;

        for(char c : csv.toCharArray()){
            switch (currentState)
            {
                case Normal:
                {
                    if( c == '\\' )
                    {
                        currentState = State.Escaped;
                    }
                    else if( c == ',' )
                    {
                        currentState = State.Comma;
                    }
                    if ( previousCharacter != null )
                    {
                        builder.append(previousCharacter);
                    }
                    previousCharacter = c;
                    break;
                }
                case Escaped:
                {
                    if(c == '\\' || c == ',')
                    {
                        // Skip the escaping '\'. Leave writing for the normal state
                        currentState = State.Normal;
                        previousCharacter = c;
                    }
                    else
                    {
                        throw new IllegalArgumentException("Error in externalLicenseIds string. A backslash (not following a backslash) should always be followed by another backslash or a comma");
                    }
                    break;
                }
                case Comma:
                {
                    externalLicenseIds.add(builder.toString());
                    builder = new StringBuilder();
                    currentState = State.Normal;
                    previousCharacter = c;
                    break;
                }
                default: break;
            }
        }

        if(previousCharacter != null)
        {
            builder.append(previousCharacter);
        }
        externalLicenseIds.add(builder.toString());

        return externalLicenseIds;
        
    }

    private static List<Integer> convertLicenseIdsCsv(String csv)
    {
        List<Integer> licenseIds = new ArrayList<Integer>();
        for( String licenseId : csv.split(",") )
        {
            int result = Integer.parseInt(licenseId);
            licenseIds.add(result);
        }
        return licenseIds;
    }

    private enum State
    {
        Normal,
        Escaped,
        Comma
    }
}
