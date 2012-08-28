package itslearning.platform.restApi.sdk.common;

import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amund
 */
public class CryptographyHelper
{

    private static final Charset utf8 = Charset.forName("UTF-8");
    public static final Charset latin1 = Charset.forName("iso-8859-1");


    public static String computeHash(ApiSession session, String sharedSecret)
    {
        return computeHash(session.getApplicationKey() + sharedSecret + session.getSessionId() + session.getTimeStamp());
    }
    
    /*
     * Computes a hash of input. Where the characters of input are interpreted as UTF-8 bytes.
     */
    public static String computeHash(String input)
    {
        return computeHash(input, utf8);
    }
    
    /*
     * @Deprecated
     * We want to use utf8 exclusively. I.e. String computeHash(String input)
     * This is for backwards compatibility during a transitional period.
     */
    @Deprecated
    public static String computeHash(String input, Charset charset)
    {
        try
        {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] md5hash;
            byte[] bytes = input.getBytes(charset);
            // Note: There was a bug here using input.length() instead of bytes.length
            // The bug was only apparent when input contained multibyte characters.
            md.update(bytes, 0, bytes.length);
            md5hash = md.digest();
            
            return convertToHex(md5hash);
            
        } 
        catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    private static String convertToHex(byte[] data)
    {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++)
        {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do
            {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                {
                    buf.append((char) ('0' + halfbyte));
                } else
                {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}
