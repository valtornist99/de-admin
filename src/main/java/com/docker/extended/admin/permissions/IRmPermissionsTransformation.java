package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.Permission;
import com.docker.extended.admin.domain.PermissionsGroup;
import com.docker.extended.admin.domain.User;
import com.docker.extended.admin.domain.UsersGroup;
import com.docker.extended.admin.domain.main.MUser;

import java.util.List;

public interface IRmPermissionsTransformation {
    //0
    MUser RmPermission(User user, Permission permission);
    //1 -> 0
    List<MUser> RmPermission(UsersGroup usersGroup, Permission permission);
    //2 -> 1
    List<MUser> RmPermission(PermissionsGroup permissionsGroup, Permission permission);
    //3 -> 2(recursion)
    List<MUser> RmParent(PermissionsGroup permissionsGroup, PermissionsGroup parent);
    //4 -> 3(self operation)
    List<MUser> RmPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup);
    //5 -> 1 + 4
    List<MUser> RmUser(UsersGroup usersGroup, User user);
}
