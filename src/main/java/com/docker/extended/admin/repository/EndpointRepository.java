package com.docker.extended.admin.repository;

import com.docker.extended.admin.domain.Endpoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EndpointRepository extends CrudRepository<Endpoint, UUID> {
}
