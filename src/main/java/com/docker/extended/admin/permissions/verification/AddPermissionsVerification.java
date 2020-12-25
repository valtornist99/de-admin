package com.docker.extended.admin.permissions.verification;

import com.docker.extended.admin.domain.PermissionsGroup;

public class AddPermissionsVerification {

    public static boolean HasSameParent(PermissionsGroup permissionsGroup, PermissionsGroup current) {
        return current.getId() == permissionsGroup.getId() ? true : current.getParents().stream().anyMatch(cur -> HasSameParent(permissionsGroup, cur));
    }
}
