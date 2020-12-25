package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.Permission;
import com.docker.extended.admin.domain.PermissionsGroup;
import com.docker.extended.admin.domain.User;
import com.docker.extended.admin.domain.UsersGroup;
import com.docker.extended.admin.domain.main.MEndpoint;
import com.docker.extended.admin.domain.main.MParam;
import com.docker.extended.admin.domain.main.MUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AddPermissionsTransformation implements IAddPermissionsTransformation {
    @Override
    public MUser AddPermission(User user, Permission permission) {
        return new MUser(user.getName(), permission.getId(),
                permission.getCommand().getEndpoints().stream().
                map(endpoint -> {
                    MEndpoint mEndpoint = new MEndpoint(endpoint.getContent());
                    mEndpoint.setMParams(permission.getParams().stream()
                            .map(param -> new MParam(param.getContent())).collect(Collectors.toList()));
                    return mEndpoint;
                }).collect(Collectors.toList())
                );
    }

    @Override
    public List<MUser> AddPermission(UsersGroup usersGroup, Permission permission) {
        return usersGroup.getUsers().stream().
                map(user -> AddPermission(user, permission)).collect(Collectors.toList());
    }

    @Override
    public List<MUser> AddPermission(PermissionsGroup permissionsGroup, Permission permission) {
        return permissionsGroup.getUsersGroups().stream().
                map(usersGroup -> AddPermission(usersGroup, permission)).
                flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public List<MUser> AddParent(PermissionsGroup permissionsGroup, PermissionsGroup parent) {
        List<Permission> permissions = AddParentsPermissions(parent.getPermissions(), parent.getParents());
        //permissions.removeAll(AddParentsPermissions(permissionsGroup.getPermissions(), permissionsGroup.getParents()));
        return permissions.stream().
                map(permission -> AddPermission(permissionsGroup, permission)).
                flatMap(List::stream).collect(Collectors.toList());
    }

    private List<Permission> AddParentsPermissions(List<Permission> permissions, List<PermissionsGroup> parents) {
        return Stream.of(permissions,
                parents.stream().map(permissionsGroup ->
                        AddParentsPermissions(permissionsGroup.getPermissions(), permissionsGroup.getParents())).
                        flatMap(List::stream).collect(Collectors.toList())).
                flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public List<MUser> AddPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup) {
        PermissionsGroup emptyPermissionsGroup = new PermissionsGroup();
        emptyPermissionsGroup.getUsersGroups().add(usersGroup);
        return AddParent(emptyPermissionsGroup, permissionsGroup);
    }

    @Override
    public List<MUser> AddUser(UsersGroup usersGroup, User user) {
        UsersGroup emptyUsersGroup = new UsersGroup();
        emptyUsersGroup.getUsers().add(user);
        List<MUser> mUsersP = usersGroup.getPermissions().stream().
                map(permission -> AddPermission(emptyUsersGroup, permission)).
                flatMap(List::stream).collect(Collectors.toList());
        List<MUser> mUsersPG = usersGroup.getPermissionsGroups().stream().
                map(permissionsGroup -> AddPermissionsGroup(emptyUsersGroup, permissionsGroup)).
                flatMap(List::stream).collect(Collectors.toList());
        mUsersP.addAll(mUsersPG);
        return mUsersP;
    }
}
