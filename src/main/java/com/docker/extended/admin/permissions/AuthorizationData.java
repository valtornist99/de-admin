package com.docker.extended.admin.permissions;

import com.docker.extended.admin.domain.main.MUser;
import com.docker.extended.admin.repository.main.MEndpointRepository;
import com.docker.extended.admin.repository.main.MParamRepository;
import com.docker.extended.admin.repository.main.MUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorizationData {

    public final MEndpointRepository mEndpointRepository;
    public final MParamRepository mParamRepository;
    public final MUserRepository mUserRepository;

    public void AddData(List<MUser> mUsers) {
        mUsers.stream().forEach(mUser -> {
            mUser.getMEndpoints().stream().forEach(mEndpoint -> {
                mEndpoint.getMParams().stream().forEach(mParam -> {
                    mParamRepository.save(mParam);
                });
                mEndpointRepository.save(mEndpoint);
            });
            mUserRepository.save(mUser);
        });
    }

    public void RmData(List<MUser> mUsers) {
        mUsers.stream().forEach(mUser -> {
            mUser.getMEndpoints().stream().forEach(mEndpoint -> {
                mEndpoint.getMParams().stream().forEach(mParam -> {
                    mParamRepository.delete(mParam);
                });
                mEndpointRepository.delete(mEndpoint);
            });
            mUserRepository.delete(mUser);
        });
    }
}
