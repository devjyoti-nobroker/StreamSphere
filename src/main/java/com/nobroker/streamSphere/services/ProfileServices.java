package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.CreateProfileDTO;
import com.nobroker.streamSphere.dtos.CreateProfileResponseDTO;
import com.nobroker.streamSphere.dtos.FindAllProfilesDTO;
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

//        accountId we will get from JWT token
        Long accountId = null;

        Account account = accountRepo.getReferenceById(accountId);
        Profile mappedProfile = profileMapper.toProfile(createProfileDTO);
        mappedProfile.setAccount(account);
        Profile dbProfile = profileRepo.save(mappedProfile);

        CreateProfileResponseDTO createProfileResponseDTO = profileMapper.toCreateProfileResponse(dbProfile);

        return createProfileResponseDTO;
    }

    public List<FindAllProfilesDTO> getAll(){

        //        accountId we will get from by JWT token
        Long accountId = null;

        List<Profile> dbProfiles = profileRepo.findByAccountId(accountId);

        return dbProfiles
                .stream()
                .map(profiles -> profileMapper.toFindAllProfile(profiles))
                .toList();
    }

}
