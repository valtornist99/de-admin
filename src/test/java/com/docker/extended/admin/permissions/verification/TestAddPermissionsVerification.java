package com.docker.extended.admin.permissions.verification;

import com.docker.extended.admin.domain.PermissionsGroup;
import com.docker.extended.admin.exception.InheritanceCycleException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestAddPermissionsVerification {

    @Test()
    public void HasSameParent() {
        PermissionsGroup permissionsGroup1 = new PermissionsGroup();
        PermissionsGroup permissionsGroup2_1 = new PermissionsGroup();
        PermissionsGroup permissionsGroup2_2 = new PermissionsGroup();
        PermissionsGroup permissionsGroup3 = new PermissionsGroup();

        permissionsGroup1.getParents().add(permissionsGroup2_1);
        permissionsGroup1.getParents().add(permissionsGroup2_2);
        permissionsGroup2_2.getParents().add(permissionsGroup3);

        assertEquals(true, AddPermissionsVerification.HasSameParent(permissionsGroup3, permissionsGroup1));
    }
}
