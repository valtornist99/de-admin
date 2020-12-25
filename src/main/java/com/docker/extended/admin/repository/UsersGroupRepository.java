package com.docker.extended.admin.repository;

import com.docker.extended.admin.domain.UsersGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersGroupRepository extends CrudRepository<UsersGroup, UUID> {
}
