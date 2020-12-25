package com.docker.extended.admin.repository;

import com.docker.extended.admin.domain.Command;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommandRepository extends CrudRepository<Command, UUID> {
    List<Command> findByContent(String content);
}
