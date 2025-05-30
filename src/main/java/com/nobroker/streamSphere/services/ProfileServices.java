package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.CreateProfileDTO;
import com.nobroker.streamSphere.dtos.CreateProfileResponseDTO;
import com.nobroker.streamSphere.mappers.ProfileMapper;
import com.nobroker.streamSphere.models.Account;
import com.nobroker.streamSphere.models.Profile;
import com.nobroker.streamSphere.repositories.AccountRepo;
import com.nobroker.streamSphere.repositories.ProfileRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServices {

    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ProfileMapper profileMapper;

    @Transactional
    public CreateProfileResponseDTO save(CreateProfileDTO createProfileDTO){

        Long accountId = 1L;

        Account account = accountRepo.getReferenceById(accountId);
        Profile mappedProfile = profileMapper.toProfile(createProfileDTO);
        mappedProfile.setAccount(account);
        Profile dbProfile = profileRepo.save(mappedProfile);

        CreateProfileResponseDTO createProfileResponseDTO = profileMapper.toCreateProfileResponse(dbProfile);

        return createProfileResponseDTO;
    }

}
