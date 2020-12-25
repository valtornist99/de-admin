package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.Command;
import com.docker.extended.admin.domain.PermissionsGroup;
import com.docker.extended.admin.domain.User;
import com.docker.extended.admin.domain.UsersGroup;
import com.docker.extended.admin.exception.InheritanceCycleException;

import java.util.List;

public interface IAddPermissions {
    void CreatePermissionsGroup(String name);
    void AddPermission(PermissionsGroup permissionsGroup, Command command, List<String> params);
    void AddParent(PermissionsGroup permissionsGroup, PermissionsGroup parent) throws InheritanceCycleException;
    void CreateUsersGroup(String name);
    void AddPermission(UsersGroup usersGroup, Command command, List<String> params);
    void AddPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup);
    void CreateUser(String name);
    void AddUser(UsersGroup usersGroup, User user);
}
