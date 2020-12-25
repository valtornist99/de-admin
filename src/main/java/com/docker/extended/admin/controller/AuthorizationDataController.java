package com.docker.extended.admin.controller;

import com.docker.extended.admin.domain.main.MUser;
import com.docker.extended.admin.repository.main.MUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization_data")
@Validated
public class AuthorizationDataController {

    public final MUserRepository mUserRepository;

    @GetMapping("/get_all")
    public Iterable<MUser> getAuthorizationData() { return mUserRepository.findAll(); }
}
