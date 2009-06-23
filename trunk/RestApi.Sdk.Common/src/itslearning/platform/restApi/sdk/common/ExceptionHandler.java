/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

import javax.xml.ws.http.HTTPException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Amund Trovåg
 */
class ExceptionHandler
{

    public static void handle(Exception ex) throws Exception
    {
        Exception exThrow = ex;

        HTTPException webEx = null;

        if (ex instanceof HTTPException)
        {
            webEx = (HTTPException) ex;
        }
        if (webEx != null)
        {
            HttpStatus httpStatus = HttpStatus.valueOf(webEx.getStatusCode());
            HttpStatusWrapper wrapper = new HttpStatusWrapper(httpStatus);
            exThrow = ExceptionTranslator.fromHttpStatus(wrapper);
        }

        throw 
         (exThrow);
    }
}
