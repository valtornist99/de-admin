package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.*;
import com.docker.extended.admin.exception.InheritanceCycleException;

import java.util.List;
import java.util.UUID;

public interface IRmPermissions {
    void DeletePermissionsGroup(PermissionsGroup permissionsGroup);
    void RmPermission(PermissionsGroup permissionsGroup, Permission permission);
    void RmParent(PermissionsGroup permissionsGroup, PermissionsGroup parent);
    void DeleteUsersGroup(UsersGroup usersGroup);
    void RmPermission(UsersGroup usersGroup, Permission permission);
    void RmPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup);
    void DeleteUser(User user);
    void RmUser(UsersGroup usersGroup, User user);
}
