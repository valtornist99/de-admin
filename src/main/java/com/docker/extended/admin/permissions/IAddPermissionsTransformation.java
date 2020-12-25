package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.Permission;
import com.docker.extended.admin.domain.PermissionsGroup;
import com.docker.extended.admin.domain.User;
import com.docker.extended.admin.domain.UsersGroup;
import com.docker.extended.admin.domain.main.MUser;

import java.util.List;

public interface IAddPermissionsTransformation {
    //0
    MUser AddPermission(User user, Permission permission);
    //1 -> 0
    List<MUser> AddPermission(UsersGroup usersGroup, Permission permission);
    //2 -> 1
    List<MUser> AddPermission(PermissionsGroup permissionsGroup, Permission permission);
    //3 -> 2(recursion)
    List<MUser> AddParent(PermissionsGroup permissionsGroup, PermissionsGroup parent);
    //4 -> 3(self operation)
    List<MUser> AddPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup);
    //5 -> 1 + 4
    List<MUser> AddUser(UsersGroup usersGroup, User user);
}
