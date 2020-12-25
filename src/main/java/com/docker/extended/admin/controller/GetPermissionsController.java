package com.docker.extended.admin.controller;

import com.docker.extended.admin.domain.PermissionsGroup;
import com.docker.extended.admin.domain.User;
import com.docker.extended.admin.domain.UsersGroup;
import com.docker.extended.admin.repository.PermissionsGroupRepository;
import com.docker.extended.admin.repository.UserRepository;
import com.docker.extended.admin.repository.UsersGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/get/permission")
@Validated
public class GetPermissionsController {

    public final PermissionsGroupRepository permissionsGroupRepository;
    public final UserRepository userRepository;
    public final UsersGroupRepository usersGroupRepository;

    @GetMapping("/permissions_groups")
    public Iterable<PermissionsGroup> getPermissionsGroups() {
        return permissionsGroupRepository.findAll();
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() { return userRepository.findAll(); }

    @GetMapping("/users_groups")
    public Iterable<UsersGroup> getUsersGroups() {
        return usersGroupRepository.findAll();
    }
}
