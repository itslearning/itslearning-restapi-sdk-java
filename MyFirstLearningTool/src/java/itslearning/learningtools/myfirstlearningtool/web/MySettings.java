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
   
    private String _sharedSecret = "79e86ecd-a012-468e-ad7c-260639ddd069";
    private String _applicationKey = "761df9d0-d9d1-4006-b928-9eb0a3c474eb";
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
