/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restApi.sdk.common;

/**
 *
 * @author Amund Trovåg
 */
class ExceptionHandler
{
    public static void handle(Exception ex) throws Exception
    {
        Exception exThrow = ex;
        // TODO: make this
        /*var webEx = ex.InnerException as WebException;

        if (webEx != null && webEx.Response != null)
        {
            var response = (HttpWebResponse) webEx.Response;

            exThrow = ExceptionTranslator.FromHttpStatus(new HttpStatus(response.StatusCode, response.StatusDescription));
        }*/

        throw (exThrow);
    }
}
