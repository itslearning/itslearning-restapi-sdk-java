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

        /// <summary>
        /// Gets the parameters from the query string.
        /// </summary>
        /// <param name="queryStringParams"></param>
        /// <returns></returns>
        public TRequestParams getParams(NameValueCollection queryStringParams)
        {
            var requestParams = Activator.CreateInstance<TRequestParams>();

            foreach (var property in GetProperties())
            {
                string value = queryStringParams[property.Name];
                if (!string.IsNullOrEmpty(value))
                {
                    Type propertyType = property.PropertyType;

                    // Nullable<T> => T
                    if (propertyType.IsGenericType && propertyType.GetGenericTypeDefinition() == typeof (Nullable<>))
                    {
                        propertyType = propertyType.GetGenericArguments()[0];
                    }

                    bool castSuccessed;
                    object castedValue = CastToPrimitiveType(value, propertyType, out castSuccessed);

                    // do not set value if convertion failed
                    if (castSuccessed)
                    {
                        property.SetValue(requestParams, castedValue, null);
                    }
                }
            }
            return requestParams;
        }
}
