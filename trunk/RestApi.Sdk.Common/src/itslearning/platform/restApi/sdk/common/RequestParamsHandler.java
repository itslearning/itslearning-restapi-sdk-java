/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restApi.sdk.common;

/**
 * Handles page parameters.
 * @author Amund Trovåg
 */
public class RequestParamsHandler<TRequestParams extends IRequestParams> {

        /**
         * Returns query string for the page without parameters.
         * @return
         */
        public String getPageUrl(TRequestParams reqParams)
        {
            return reqParams.getPageUrl();
        }
        // TODO: it seems that this reflection bit might be hard in java
}
