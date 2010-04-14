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

    public static String computeHash(ApiSession session, String sharedSecret)
    {
        return computeHash(session.getApplicationKey() + sharedSecret + session.getSessionId() + session.getTimeStamp());
    }

    public static String computeHash(String input)
    {
        try
        {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] md5hash = new byte[32];
            md.update(input.getBytes(Charset.forName("iso-8859-1")), 0, input.length());
            md5hash = md.digest();
            return convertToHex(md5hash);
        } catch (NoSuchAlgorithmException ex)
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
