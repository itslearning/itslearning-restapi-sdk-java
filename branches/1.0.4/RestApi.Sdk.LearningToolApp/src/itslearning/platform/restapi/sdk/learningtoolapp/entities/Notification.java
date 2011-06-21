package itslearning.platform.restapi.sdk.learningtoolapp.entities;

import java.util.HashMap;
import itslearning.platform.restApi.sdk.common.entities.Constants.ElementPermission;

/**
 * A Notification is a message that the learning tool can send
 * to the itslearning users. It will appear in a content block, RSS feed
 * etc. It is meant to notify the user of e.g. a new assessment
 * being available on a hand in via the learning tool or that someone has
 * commented on some work you made within the learning tool etc.
 *
 * @author kschmidt
 */
public class Notification {

    /**
     *  Who will receive the notification.
     *  If 'Modifier': only users with Modify permission to this element.
     */
    private ElementPermission _reciverPermission;

    /**
     *  Additional parameter included in the Launch-url
     */
    private String _launchParameter;

    /**
     * Message in default language, max 80 chars per language
     */
    private String _message;

    /**
     * Message in different languages.
     * Dictionary  LanguageName=>message
     */
    private HashMap<String, String> _localizedMessages;

    /**
     * @return the _reciverPermission
     */
    public ElementPermission getReciverPermission() {
        return _reciverPermission;
    }

    /**
     * @param reciverPermission the _reciverPermission to set
     */
    public void setReciverPermission(ElementPermission reciverPermission) {
        this._reciverPermission = reciverPermission;
    }

    /**
     * @return the _launchParameter
     */
    public String getLaunchParameter() {
        return _launchParameter;
    }

    /**
     * @param launchParameter the _launchParameter to set
     */
    public void setLaunchParameter(String launchParameter) {
        this._launchParameter = launchParameter;
    }

    /**
     * @return the _message
     */
    public String getMessage() {
        return _message;
    }

    /**
     * @param message the _message to set
     */
    public void setMessage(String message) {
        this._message = message;
    }

    /**
     * @return the _localizedMessages
     */
    public HashMap<String, String> getLocalizedMessages() {
        return _localizedMessages;
    }

    /**
     * @param localizedMessages the _localizedMessages to set
     */
    public void setLocalizedMessages(HashMap<String, String> localizedMessages) {
        this._localizedMessages = localizedMessages;
    }
}
