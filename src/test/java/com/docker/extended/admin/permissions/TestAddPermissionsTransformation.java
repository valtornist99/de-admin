package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.*;
import com.docker.extended.admin.domain.main.MUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestAddPermissionsTransformation {

    @Test
    public void AddParent() {
        IAddPermissionsTransformation addPermissionsTransformation = new AddPermissionsTransformation();

        Command command = new Command("command0");
        command.getEndpoints().add(new Endpoint("endpoint0", true));

        PermissionsGroup permissionsGroup = new PermissionsGroup();
        PermissionsGroup parent0 = new PermissionsGroup();
        PermissionsGroup parent1 = new PermissionsGroup();
        PermissionsGroup parent2 = new PermissionsGroup();
        PermissionsGroup parent3 = new PermissionsGroup();

        User user = new User("user0");
        UsersGroup usersGroup = new UsersGroup();
        usersGroup.getUsers().add(user);

        permissionsGroup.getUsersGroups().add(usersGroup);

        parent0.getParents().add(parent1);
        parent0.getParents().add(parent2);
        parent2.getParents().add(parent3);

        Permission permission = new Permission(command, new ArrayList<>());
        Permission permission0 = new Permission(command, new ArrayList<>());
        Permission permission1 = new Permission(command, new ArrayList<>());
        Permission permission2 = new Permission(command, new ArrayList<>());
        Permission permission3 = new Permission(command, new ArrayList<>());

        permissionsGroup.getPermissions().add(permission);
        parent0.getPermissions().add(permission0);
        parent1.getPermissions().add(permission1);
        parent2.getPermissions().add(permission2);
        parent3.getPermissions().add(permission3);

        List<MUser> mUsers = addPermissionsTransformation.AddParent(permissionsGroup, parent0);
        List<UUID> actual = mUsers.stream().map(mUser -> mUser.getPermissionId()).sorted().collect(Collectors.toList());
        log.debug("actual: " + actual.toString());

        List<UUID> expected = Arrays.asList(permission0.getId(), permission1.getId(), permission2.getId(),permission3.getId()).
                stream().sorted().collect(Collectors.toList());

        assertEquals(expected, actual);
    }
}
