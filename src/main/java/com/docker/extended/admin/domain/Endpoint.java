package com.docker.extended.admin.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Endpoint {
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    @Id
    private final UUID id = UUID.randomUUID();
    private final String content;
    private final boolean optional;
}
