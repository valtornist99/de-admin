package com.docker.extended.admin.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UsersGroup {
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    @Id
    private final UUID id = UUID.randomUUID();
    @NonNull
    private String name;
    @ManyToMany
    private final List<User> users = new ArrayList<>();
    @OneToMany
    private List<Permission> permissions = new ArrayList<>();
    @ManyToMany
    private List<PermissionsGroup> permissionsGroups = new ArrayList<>();
}
