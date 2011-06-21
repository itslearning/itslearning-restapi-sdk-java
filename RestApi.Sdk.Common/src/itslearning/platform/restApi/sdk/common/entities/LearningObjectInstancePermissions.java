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

    /**
     * This permission is set if user should be allowed to read content.
     * It should always be set as long as authentication has been successful
     */
    public static final String READ = "READ";

    /**
     * Only set for "Learning activity" applications.
     * "Learning resource" applications will never have this permission.
     * This permission is set if a user has permission to participate, it should be checked to e.g. decide if user
     * should be allowed to submit answer to a test. Students and teachers in an it's learning course will normally
     * have this permission.
     */
    public static final String PARTICIPATE = "PARTICIPATE";

    /**
     * Only set for "Learning activity" applications.
     * "Learning resource" applications will never have this permission.
     * This permission is set if a user has permission to evaluate, it should be checked to e.g. decide if user
     * should be allowed to review and evaluate answers to a test. Teachers in an it's learning course will normally
     * have this permission.
     */
    public static final String EVALUATE = "EVALUATE";

    /**
     * This permission is set if user should be allowed to edit instance content stored in your application and also e.g. update deadline
     * stored in it's learning (use rest api method). Teachers in an it's learning course will normally have this permission.
     * If your application supports sharing - the user will not have Modify permission when accessing an instance that
     * is connected to a library "master" instance, even though user is author. Shared content must be edited from the library.
     */
    public static final String MODIFY = "MODIFY";

    /**
     * This permission will only be used if your application supports sharing.
     * This permission is set if user is allowed to e.g. update deadline stored in it's learning (use rest api method). But
     * user should <b>not</b> be allowed to edit instance content stored in your application with this permission.
     * This permission can only be set if user is accessing a shared instance inside a course.
     */
    public static final String MODIFYINSTANCE = "MODIFYINSTANCE";

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
