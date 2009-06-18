/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

/**
 *
 * @author Amund Trovåg
 */
public class Settings
{

    // TODO: C# code also implements DefaultSettings class where it's read from web.config/app.config. Might do something with spring? Other "usual" way of reading configs in java?

    /**
     * Interface to provive application settings to Rest API client.
     */
    public interface IApplicationSettings
    {
        /**
         * Secret shared between application and it's learning.
         * @return
         */
        public String GetSharedSecret();

        /**
         * Application key.
         */
        public String GetApplicationKey();

        /**
         * Defines how long request to the API is valid.
         * @return
         */
        public int GetRequestLifetimeInMinutes();
    }
}
