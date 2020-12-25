package com.docker.extended.admin.repository;

import com.docker.extended.admin.domain.PermissionsGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionsGroupRepository extends CrudRepository<PermissionsGroup, UUID> {

}
