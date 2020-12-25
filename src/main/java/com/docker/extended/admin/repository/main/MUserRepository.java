package com.docker.extended.admin.repository.main;

import com.docker.extended.admin.domain.main.MUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MUserRepository extends CrudRepository<MUser, UUID> {
}
