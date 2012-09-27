/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 *
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class EntityConstants {

    public static final String NAMESPACE_ENTITIES = "http://schemas.datacontract.org/2004/07/Itslearning.Platform.RestApi.Sdk.LearningToolApp.Entities";
    public static final String NAMESPACE_ARRAYS = "http://schemas.microsoft.com/2003/10/Serialization/Arrays";

    /**
     * Order for sorting.
     */
    public enum OrderDirection
    {
        Asc,
        Desc
    }
}

