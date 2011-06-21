package itslearning.platform.restApi.sdk.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles page parameters.
 * @author Amund Trovåg
 */
public class RequestParamsHandler<TRequestParams extends IRequestParams>
{
    // TODO: it seems that this reflection bit might be hard in java

    /**
     * Gets the parameters from the query string.
     * @param <TRequestParams>
     * @param queryStringParams
     * @param clazz
     * @return
     */
    public static <TRequestParams> TRequestParams getParams(Map queryStringParams, Class<TRequestParams> clazz)
    {

        TRequestParams reqParams = null;
        try
        {
            reqParams = clazz.newInstance();
        } catch (InstantiationException ex)
        {
            Logger.getLogger(RequestParamsHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            Logger.getLogger(RequestParamsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        Method[] methods = reqParams.getClass().getMethods();
        for (Method m : methods)
        {
            // 1. Only use the setters
            if (m.getName().startsWith("set"))
            {
                // 2. If setter, get the fieldname in uppercase and see if it's in queryStringParams map
                String fieldName = m.getName().replaceFirst("set", "").toUpperCase();

                for (Object s : queryStringParams.keySet())
                {
                    boolean found = false;
                    String upperString = ((String) s).toUpperCase();
                    if (upperString.equals(fieldName))
                    {
                        // 3. If match, get value from reqParams as string with case-insensitive comparison(!)

                        String[] values = (String[]) queryStringParams.get(s);
                        String value = values[0];
                        try
                        {
                            // 4. cast to correct class or primitive, and
                            // 5. invoke setter with value
                            Class<?> paramClass = m.getParameterTypes()[0];
                            if (paramClass.isAssignableFrom(value.getClass()))
                            {
                                // value is assignable to method
                                m.invoke(reqParams, value);
                            }
                            else
                            {
                                // TODO: this doesn't cover everything, and isn't truly general enough. It will do for now though
                                if (paramClass.isInstance(new Boolean(false)))
                                {
                                    m.invoke(reqParams, new Boolean(value));
                                }
                                else if (paramClass.isInstance(new Integer(0)))
                                {
                                    m.invoke(reqParams, new Integer(value));
                                }
                            }

                            break;
                        } catch (IllegalAccessException ex)
                        {
                            throw new RuntimeException("Failed to set value of: " + fieldName + " with value: " + value);
                        } catch (IllegalArgumentException ex)
                        {
                            throw new RuntimeException("Failed to set value of: " + fieldName + " with value: " + value);
                        } catch (InvocationTargetException ex)
                        {
                            throw new RuntimeException("Failed to set value of: " + fieldName + " with value: " + value);
                        }
                    }
                }
            }
        }

        return reqParams;
    }
}
