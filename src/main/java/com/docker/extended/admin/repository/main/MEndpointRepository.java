package com.docker.extended.admin.repository.main;

import com.docker.extended.admin.domain.main.MEndpoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MEndpointRepository extends CrudRepository<MEndpoint, UUID> {
}
