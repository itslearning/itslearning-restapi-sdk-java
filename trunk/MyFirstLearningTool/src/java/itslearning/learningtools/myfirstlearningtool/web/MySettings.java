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
   
    private String _sharedSecret = "74e75869-af9d-43a4-8450-74f1c7c4d45a";
    private String _applicationKey = "773bfcc7-ee4a-47e2-b996-1aacc1537d6b";
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
