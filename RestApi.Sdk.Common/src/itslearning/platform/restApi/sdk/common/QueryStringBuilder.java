package itslearning.platform.restApi.sdk.common;

import java.util.Hashtable;

/**
 *
 * @author Amund Trovåg
 */
/// <summary>
///  Builds query string based on URL and parameters.
/// </summary>
public final class QueryStringBuilder
{
    private boolean _encodeAmpersand;
    private Hashtable<String, String> _parameters = new Hashtable<String, String>();
    private String _url;

    public QueryStringBuilder(String url, boolean encodeAmpersand)
    {
        _url = url;
        _encodeAmpersand = encodeAmpersand;
    }

    public String getQueryString()
    {
        String ampersand = _encodeAmpersand ? "&amp;" : "&";
        StringBuilder sb = new StringBuilder(_url);

        if (_parameters.size() > 0)
        {
            sb.append(_url.contains("?") ? ampersand : "?");
        }
        for( String key : _parameters.keySet()){
            sb.append(String.format("%s=%s%s", key, _parameters.get(key), ampersand));
        }
        if(_parameters.size()>0){
            sb.setLength(sb.length() - ampersand.length());
        }

        return sb.toString();
    }

    /**
     * Add a key-value pair to the base url of the object
     * @param key
     * @param value
     */
    public void AddParameter(String key, String value)
    {
        _parameters.put(key, value);
    }
}
