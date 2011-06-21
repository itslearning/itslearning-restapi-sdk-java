package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.entities.ApiSession;

/**
 * Helper class for ApiSession serialization.
 * @author Amund Trovåg <amund@itslearning.com>
 */
class AuthorizationHelper
{

    /**
     * Serialize session to insert to Authorization header.
     * @param session API session
     * @return Serialized session
     */
    public static String toAuthorizationHeader(ApiSession session)
    {
        if (session == null)
        {
            throw new IllegalArgumentException("session was null");
        }

        return String.format("%s#%s#%s#%s",
                session.getApplicationKey(),
                session.getSessionId(),
                session.getTimeStamp(),
                session.getHash());
    }

    /**
     * Restores ApiSession from Authorization header.
     * @param authHeaderData Session data
     * @return Authorization header for session authentication
     */
    public static ApiSession getApiSession(String[] authHeaderData)
    {
        ApiSession session = ApiSession.createApiSession(authHeaderData[1]);

        session.setApplicationKey(authHeaderData[0]);
        session.setLastRequestDateTimeUtc(ApiSession.parseTimeStamp(authHeaderData[2]));
        session.setHash(authHeaderData[3]);

        return session;
    }
}
