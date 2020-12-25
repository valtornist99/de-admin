package com.docker.extended.admin.controller;

import com.docker.extended.admin.domain.*;
import com.docker.extended.admin.exception.InheritanceCycleException;
import com.docker.extended.admin.permissions.IAddPermissions;
import com.docker.extended.admin.repository.CommandRepository;
import com.docker.extended.admin.repository.PermissionsGroupRepository;
import com.docker.extended.admin.repository.UserRepository;
import com.docker.extended.admin.repository.UsersGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/add/permission")
@Validated
public class AddPermissionsController {

    public final IAddPermissions addPermissions;

    public final CommandRepository commandRepository;
    public final PermissionsGroupRepository permissionsGroupRepository;
    public final UserRepository userRepository;
    public final UsersGroupRepository usersGroupRepository;

    @PostMapping("/permissions_group")
    public ResponseEntity<Void> CreatePermissionsGroup(@RequestParam String name) {
        addPermissions.CreatePermissionsGroup(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/permissions_group/permission")
    public ResponseEntity<Void> AddPermissionPG(@RequestParam String permissionsGroupId, @RequestParam String commandContent, @RequestParam List<String> params) {
        PermissionsGroup permissionsGroup = permissionsGroupRepository.findById(UUID.fromString(permissionsGroupId)).get();
        Command command = commandRepository.findByContent(commandContent).get(0);
        addPermissions.AddPermission(permissionsGroup, command, params);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/permissions_group/parent")
    public ResponseEntity<Void> AddParent(@RequestParam String permissionsGroupId, @RequestParam String parentId) {
        PermissionsGroup permissionsGroup = permissionsGroupRepository.findById(UUID.fromString(permissionsGroupId)).get();
        PermissionsGroup parent = permissionsGroupRepository.findById(UUID.fromString(parentId)).get();
        try {
            addPermissions.AddParent(permissionsGroup, parent);
            return ResponseEntity.ok().build();
        }
        catch (InheritanceCycleException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/users_group")
    public ResponseEntity<Void> CreateUsersGroup(@RequestParam String name) {
        addPermissions.CreateUsersGroup(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users_group/permission")
    public ResponseEntity<Void> AddPermissionUG(@RequestParam String usersGroupId, @RequestParam String commandContent, @RequestParam List<String> params) {
        UsersGroup usersGroup = usersGroupRepository.findById(UUID.fromString(usersGroupId)).get();
        Command command = commandRepository.findByContent(commandContent).get(0);
        addPermissions.AddPermission(usersGroup, command, params);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users_group/permissions_group")
    public ResponseEntity<Void> AddPermissionsGroup(@RequestParam String usersGroupId, @RequestParam String permissionsGroupId) {
        UsersGroup usersGroup = usersGroupRepository.findById(UUID.fromString(usersGroupId)).get();
        PermissionsGroup permissionsGroup = permissionsGroupRepository.findById(UUID.fromString(permissionsGroupId)).get();
        addPermissions.AddPermissionsGroup(usersGroup, permissionsGroup);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<Void> CreateUser(@RequestParam String name) {
        addPermissions.CreateUser(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users_group/user")
    public ResponseEntity<Void> AddUser(@RequestParam String usersGroupId, @RequestParam String userId) {
        UsersGroup usersGroup = usersGroupRepository.findById(UUID.fromString(usersGroupId)).get();
        User user = userRepository.findById(UUID.fromString(userId)).get();
        addPermissions.AddUser(usersGroup, user);
        return ResponseEntity.ok().build();
    }
}
