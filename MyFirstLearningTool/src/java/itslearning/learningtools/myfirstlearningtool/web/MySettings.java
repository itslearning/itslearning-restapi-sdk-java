/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.learningtools.myfirstlearningtool.web;

import itslearning.platform.restApi.sdk.common.Settings.IApplicationSettings;

/**
 *
 * Simple implementation of the IApplicationSettings interface
 * @author Amund Trovåg
 */
public class MySettings implements IApplicationSettings {
   
    private String _applicationKey = "37f38b68-930d-4b23-bb36-7e83afb69681";
    private String _sharedSecret = "63dbcca0-32a3-4566-ae52-14b4b59ebb56";

    // NOTE: change this to the correct base url when you use it. This will of course change from environment
    String baseUrl = "http://betarest.itslearning.com/restApi";
    
    private int _requestLifeTimeInMinutes = 15;
    public String getSharedSecret()
    {
        return _sharedSecret;
    }

    public String getApplicationKey()
    {
        return _applicationKey;
    }

    public int getRequestLifetimeInMinutes() throws Exception
    {
        return _requestLifeTimeInMinutes;
    }
}
