/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.learningtools.myfirstlearningtool.web;

import itslearning.platform.restApi.sdk.common.Settings.IApplicationSettings;

/**
 *
 * @author Amund Trovåg
 */
public class MySettings implements IApplicationSettings {
   
    private String _sharedSecret = "c14ba64d-5a6d-499f-836e-52a07c41d3dc";
    private String _applicationKey = "5c4a6404-528e-48bd-90ee-b3bea6a1e772";
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
