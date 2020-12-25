package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.Permission;
import com.docker.extended.admin.domain.PermissionsGroup;
import com.docker.extended.admin.domain.User;
import com.docker.extended.admin.domain.UsersGroup;
import com.docker.extended.admin.domain.main.MEndpoint;
import com.docker.extended.admin.domain.main.MUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RmPermissionsTransformation implements IRmPermissionsTransformation {

    @Override
    public MUser RmPermission(User user, Permission permission) {
        return new MUser(user.getName(), permission.getId(),
                permission.getCommand().getEndpoints().stream().map(endpoint -> new MEndpoint(endpoint.getContent())).collect(Collectors.toList()));
    }

    @Override
    public List<MUser> RmPermission(UsersGroup usersGroup, Permission permission) {
        return usersGroup.getUsers().stream().
                map(user -> RmPermission(user, permission)).collect(Collectors.toList());
    }

    @Override
    public List<MUser> RmPermission(PermissionsGroup permissionsGroup, Permission permission) {
        return permissionsGroup.getUsersGroups().stream().
                map(usersGroup -> RmPermission(usersGroup, permission)).
                flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public List<MUser> RmParent(PermissionsGroup permissionsGroup, PermissionsGroup parent) {
        List<Permission> permissions = RmParentsPermissions(parent.getPermissions(), parent.getParents());
        return permissions.stream().
                map(permission -> RmPermission(permissionsGroup, permission)).
                flatMap(List::stream).collect(Collectors.toList());
    }

    private List<Permission> RmParentsPermissions(List<Permission> permissions, List<PermissionsGroup> parents) {
        return Stream.of(permissions,
                parents.stream().map(permissionsGroup ->
                        RmParentsPermissions(permissionsGroup.getPermissions(), permissionsGroup.getParents())).
                        flatMap(List::stream).collect(Collectors.toList())).
                flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public List<MUser> RmPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup) {
        PermissionsGroup emptyPermissionsGroup = new PermissionsGroup();
        emptyPermissionsGroup.getUsersGroups().add(usersGroup);
        return RmParent(emptyPermissionsGroup, permissionsGroup);
    }

    @Override
    public List<MUser> RmUser(UsersGroup usersGroup, User user) {
        UsersGroup emptyUsersGroup = new UsersGroup();
        emptyUsersGroup.getUsers().add(user);
        List<MUser> mUsersP = usersGroup.getPermissions().stream().
                map(permission -> RmPermission(emptyUsersGroup, permission)).
                flatMap(List::stream).collect(Collectors.toList());
        List<MUser> mUsersPG = usersGroup.getPermissionsGroups().stream().
                map(permissionsGroup -> RmPermissionsGroup(emptyUsersGroup, permissionsGroup)).
                flatMap(List::stream).collect(Collectors.toList());
        mUsersP.addAll(mUsersPG);
        return mUsersP;
    }
}
