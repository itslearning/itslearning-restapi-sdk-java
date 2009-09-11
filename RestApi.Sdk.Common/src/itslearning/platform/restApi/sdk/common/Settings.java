package itslearning.platform.restApi.sdk.common;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Amund Trovåg
 */
public class Settings
{

    /**
     * Interface to provive application settings to Rest API client.
     */
    public interface IApplicationSettings
    {

        /**
         * Secret shared between application and it's learning.
         * @return
         */
        public String getSharedSecret();

        /**
         * Application key.
         */
        public String getApplicationKey();

        /**
         * Defines how long request to the API is valid.
         * @return
         */
        public int getRequestLifetimeInMinutes() throws Exception;
    }

    /// <summary>
    /// Helper class for reading standard configuration settings from application configuration file.
    /// </summary>
    public class DefaultSettings extends HttpServlet implements IApplicationSettings
    {

        ServletConfig config;
        ServletContext application;
        /// <summary>
        /// Secret shared between application and it's learning.
        /// </summary>

        public DefaultSettings(ServletConfig config) throws ServletException
        {
            this.config = config;
            super.init(config);
            // TODO: does this work, and is it a good way to go with all web frameworks etc? Maybe the class is not needed anyway.
            application = config.getServletContext();
        }

        /**
         * Shared secret
         * @return
         */
        public String getSharedSecret()
        {
            return application.getInitParameter("SharedSecret");
        }

        /**
         * Application key.
         * @return
         */
        public String getApplicationKey()
        {
            return application.getInitParameter("ApplicationKey");
        }

        /**
         * Defines how long request to the API is valid.
         * @return
         * @throws java.lang.Exception
         */
        public int getRequestLifetimeInMinutes() throws Exception
        {
            String temp = application.getInitParameter("RequestLifetimeInMinutes");
            if (temp == null || temp.length()<1)
            {
                throw new Exception("RequestLifetimeInMinutes was not found in appSettings.");
            }
            int result;
            try
            {
                result = Integer.parseInt(temp);
            } catch (NumberFormatException nfe)
            {
                throw new NumberFormatException("RequestLifetimeInMinutes must be valid integer number.");
            }
            return result;
        }
    }
}
