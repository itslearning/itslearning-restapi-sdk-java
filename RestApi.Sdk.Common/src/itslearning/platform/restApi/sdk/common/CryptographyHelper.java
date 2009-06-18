/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

import itslearning.platform.restApi.sdk.common.entities.ApiSession;

/**
 *
 * @author amund
 */
public class CryptographyHelper
{

    public static String computeHash(ApiSession session, String sharedSecret)
    {
        return computeHash(session.getApplicationKey() + sharedSecret + session.getSessionId() + session.GetTimeStamp());
    }

    public static String computeHash(String input)
    {
        // TODO make algorithm
        /*var md5Hasher = new MD5CryptoServiceProvider();
        byte[] data = md5Hasher.ComputeHash(Encoding.Default.GetBytes(input));
        var sb = new StringBuilder();

        // Loop through each byte of the hashed data and format each one as a hexadecimal string.
        for (int i = 0; i < data.Length; i++)
        {
            sb.Append(data[i].ToString("x2"));
        }

        return sb.ToString();*/
        return "not implemented";
    }
}
