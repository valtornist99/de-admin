//package com.docker.extended.admin.permissions;
//
//import com.docker.extended.admin.domain.Permission;
//import com.docker.extended.admin.domain.PermissionsGroup;
//import com.docker.extended.admin.domain.User;
//import com.docker.extended.admin.domain.UsersGroup;
//import com.docker.extended.admin.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class RmPermissions implements IRmPermissions {
//
//    public final IRmPermissionsTransformation rmPermissionsTransformation;
//
//    public final CommandRepository commandRepository;
//    public final EndpointRepository endpointRepository;
//    public final ParamRepository paramRepository;
//    public final PermissionRepository permissionRepository;
//    public final PermissionsGroupRepository permissionsGroupRepository;
//    public final UserRepository userRepository;
//    public final UsersGroupRepository usersGroupRepository;
//
//    @Override
//    public void DeletePermissionsGroup(PermissionsGroup permissionsGroup) {
//        permissionsGroup.getPermissions().stream().forEach(permission -> RmPermission(permissionsGroup, permission));
//        permissionsGroupRepository.delete(permissionsGroup);
//    }
//
//    @Override
//    public void RmPermission(PermissionsGroup permissionsGroup, Permission permission) {
//        rmPermissionsTransformation.RmPermission(permissionsGroup, permission);
//        permissionsGroup.getPermissions().remove(permission);
//        permissionsGroupRepository.save(permissionsGroup);
//    }
//
//    @Override
//    public void RmParent(PermissionsGroup permissionsGroup, PermissionsGroup parent) {
//        rmPermissionsTransformation.RmParent(permissionsGroup, parent);
//        permissionsGroup.getParents().remove(parent);
//        permissionsGroupRepository.save(permissionsGroup);
//    }
//
//    @Override
//    public void DeleteUsersGroup(UsersGroup usersGroup) {
//        usersGroup.getUsers().stream().forEach(user -> RmUser(usersGroup, user));
//        usersGroup.getPermissions().stream().forEach(permission -> permissionRepository.delete(permission));
//        usersGroupRepository.delete(usersGroup);
//    }
//
//    @Override
//    public void RmPermission(UsersGroup usersGroup, Permission permission) {
//        rmPermissionsTransformation.RmPermission(usersGroup, permission);
//        usersGroup.getPermissions().remove(permission);
//        permissionRepository.delete(permission);
//        usersGroupRepository.save(usersGroup);
//    }
//
//    @Override
//    public void RmPermissionsGroup(UsersGroup usersGroup, PermissionsGroup permissionsGroup) {
//        rmPermissionsTransformation.RmPermissionsGroup(usersGroup, permissionsGroup);
//        usersGroup.getPermissionsGroups().remove(permissionsGroup);
//        usersGroupRepository.save(usersGroup);
//    }
//
//    @Override
//    public void DeleteUser(User user) {
//        usersGroupRepository.findAll().forEach(usersGroup -> RmUser(usersGroup, user));
//    }
//
//    @Override
//    public void RmUser(UsersGroup usersGroup, User user) {
//        rmPermissionsTransformation.RmUser(usersGroup, user);
//        usersGroup.getUsers().remove(user);
//        usersGroupRepository.save(usersGroup);
//    }
//}
