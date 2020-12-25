package com.docker.extended.admin.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Setter
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Command {
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    @Id
    private final UUID id = UUID.randomUUID();
    private final String content;
    @OneToMany
    private final List<Endpoint> endpoints = new ArrayList<>();
}
