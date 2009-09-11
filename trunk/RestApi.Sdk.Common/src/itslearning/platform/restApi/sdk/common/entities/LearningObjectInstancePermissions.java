package itslearning.platform.restApi.sdk.common.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to contain all permissions retrieved from it's learning
 * @author Amund Trovåg <amund@itslearning.com>
 */
public class LearningObjectInstancePermissions implements Serializable
{

    public static final String NONE = "NONE";
    public static final String READ = "READ";
    public static final String PARTICIPATE = "PARTICIPATE";
    public static final String EVALUATE = "EVALUATE";
    public static final String MODIFY = "MODIFY";
    private List<String> _permissions = new ArrayList<String>();

    /**
     * Empties the permissionslist
     */
    public void clearPermissions(){
        _permissions.clear();
    }

    /**
     * Adds a list of permissions. Skips permissions that exist from before.
     * @param permission
     */
    public void addPermission(String[] permissions)
    {
        for (String p : permissions)
        {
            addPermission(p);
        }
    }

    /**
     * Adds a permission if it's a new one that doesn't exist from before
     * @param permission
     */
    public void addPermission(String permission)
    {
        if (!_permissions.contains(permission))
        {
            _permissions.add(permission.trim());
        }
    }

    /**
     * Checks if specified permission is in permissionslist
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission)
    {
        return _permissions.contains(permission);
    }
}
