/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.learningtools.myfirstlearningtool.web;

import itslearning.platform.restapi.sdk.learningtoolapp.CommunicationHelper;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Base class for all pages to ease communicating with it's learning API.
 * @author Amund Trovåg
 */
public class BaseServlet extends HttpServlet
{

    /**
     *   1. Checks if the request is not expired. If it is expired, SecurityAccessDeniedException is thrown.
    2. Validates the signature. If it is not valid, SecurityAccessDeniedException is thrown.
    3. Puts ApiSessionId into your application session state.
    4. Puts ApiSession object into your application session state (it it useful for RestApi.Client .NET wrapper assembly).
    5. Puts all other received parameters into your application session state
    (UserId, FirstName, LastName, Locale, Language, Use12hTimeFormat,
    Accessibility, Permissions,
    WindowsTimeZoneId, OlsonTimeZoneId).
    To validate timestamp and signature the method reads application key,
    shared secret and request lifetime from a source implementing IApplicationSettings interface.
     * @param request
     */
    protected void createSession(HttpServletRequest request)
    {
        CommunicationHelper.initApiSession(request, new MySettings());
    }

    /**
     * True, if API session has been created and put in to session.
     * @param request
     * @return
     */
    protected boolean isApiSessionCreated(HttpServletRequest request)
    {
        return CommunicationHelper.GetApiSessionId(request) != null && !CommunicationHelper.GetApiSessionId(request).isEmpty();
    }
}
