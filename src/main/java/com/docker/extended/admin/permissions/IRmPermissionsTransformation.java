package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.Permission;
import com.docker.extended.admin.domain.PermissionsGroup;
import com.docker.extended.admin.domain.User;
import com.docker.extended.admin.domain.UsersGroup;
import com.docker.extended.admin.domain.main.MUser;

import java.util.List;

public interface IRmPermissionsTransformation {

    MUser RmPermission(User user, Permission permission);
    List<MUser> RmPermission(UsersGroup usersGroup, Permission permission);
    List<MUser> RmPermission(PermissionsGroup permissionsGroup, Permission permission);
    List<MUser> RmParent(PermissionsGroup permissionsGroup, PermissionsGroup parent);
    List<MUser> RmPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup);
    List<MUser> RmUser(UsersGroup usersGroup, User user);
}
