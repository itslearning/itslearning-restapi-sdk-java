/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itslearning.platform.restApi.sdk.common;

import itslearning.platform.restApi.sdk.common.entities.Constants.LearningObjectInstancePermissions;

/**
 * Common serialization logic.
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class SerializationHelper {
    /**
     * Serializes LearningObjectInstancePermissions flags as a string.
     * @param permissions
     * @return Serialized permissions
     */
        public static String learningObjectInstancePermissionsToString(LearningObjectInstancePermissions permissions)
        {
            // The same code is in ItslUserInfo.ToString method

            StringBuilder sb = new StringBuilder();
            /*
            if ((permissions & LearningObjectInstancePermissions.READ) != 0)
                sb.append("R");
            if ((permissions & LearningObjectInstancePermissions.PARTICIPATE) != 0)
                sb.append("P");
            if ((permissions & LearningObjectInstancePermissions.EVALUATE) != 0)
                sb.append("E");
            if ((permissions & LearningObjectInstancePermissions.MODIFY) != 0)
                sb.append("M");
*/
            return sb.toString();
        }
}
