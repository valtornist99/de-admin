package com.docker.extended.admin.permissions;

import com.docker.extended.admin.permissions.verification.AddPermissionsVerification;
import com.docker.extended.admin.domain.*;
import com.docker.extended.admin.domain.main.MUser;
import com.docker.extended.admin.exception.InheritanceCycleException;
import com.docker.extended.admin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AddPermissions implements IAddPermissions {

    public final IAddPermissionsTransformation addPermissionsTransformation;

    public final CommandRepository commandRepository;
    public final EndpointRepository endpointRepository;
    public final ParamRepository paramRepository;
    public final PermissionRepository permissionRepository;
    public final PermissionsGroupRepository permissionsGroupRepository;
    public final UserRepository userRepository;
    public final UsersGroupRepository usersGroupRepository;

    public final AuthorizationData authorizationData;

    @Override
    public void CreatePermissionsGroup(String name) {
        permissionsGroupRepository.save(new PermissionsGroup(name));
    }

    @Override
    public void AddPermission(PermissionsGroup permissionsGroup, Command command, List<String> params) {
        List<Param> paramT = StringsToParams(params);
        Permission permission = new Permission(command, paramT);
        List<MUser> mUsers = addPermissionsTransformation.AddPermission(permissionsGroup, permission);
        paramRepository.saveAll(paramT);
        permissionRepository.save(permission);
        permissionsGroup.getPermissions().add(permission);
        permissionsGroupRepository.save(permissionsGroup);
        authorizationData.AddData(mUsers);
    }

    @Override
    public void AddParent(PermissionsGroup permissionsGroup, PermissionsGroup parent) throws InheritanceCycleException {
        if(AddPermissionsVerification.HasSameParent(permissionsGroup, parent)) { throw new InheritanceCycleException(); }
        List<MUser> mUsers = addPermissionsTransformation.AddParent(permissionsGroup, parent);
        permissionsGroup.getParents().add(parent);
        permissionsGroupRepository.save(permissionsGroup);
        authorizationData.AddData(mUsers);
    }

    @Override
    public void CreateUsersGroup(String name) { usersGroupRepository.save(new UsersGroup(name)); }

    @Override
    public void AddPermission(UsersGroup usersGroup, Command command, List<String> params) {
        List<Param> paramT = StringsToParams(params);
        Permission permission = new Permission(command, paramT);
        List<MUser> mUsers = addPermissionsTransformation.AddPermission(usersGroup, permission);
        paramRepository.saveAll(paramT);
        permissionRepository.save(permission);
        usersGroup.getPermissions().add(permission);
        usersGroupRepository.save(usersGroup);
        authorizationData.AddData(mUsers);
    }

    @Override
    public void AddPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup) {
        List<MUser> mUsers = addPermissionsTransformation.AddPermissionsGroup(usersGroup, permissionsGroup);
        usersGroup.getPermissionsGroups().add(permissionsGroup);
        permissionsGroup.getUsersGroups().add(usersGroup);
        usersGroupRepository.save(usersGroup);
        permissionsGroupRepository.save(permissionsGroup);
        authorizationData.AddData(mUsers);
    }

    @Override
    public void CreateUser(String name) { userRepository.save(new User(name)); }

    @Override
    public void AddUser(UsersGroup usersGroup, User user) {
        List<MUser> mUsers = addPermissionsTransformation.AddUser(usersGroup, user);
        usersGroup.getUsers().add(user);
        usersGroupRepository.save(usersGroup);
        authorizationData.AddData(mUsers);
    }

    private List<Param> StringsToParams(List<String> params) { return params.stream().map(param -> new Param(param)).collect(Collectors.toList()); }


}
