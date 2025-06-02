package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.ProfileDTO;
import com.nobroker.streamSphere.mappers.ProfileMapper;
import com.nobroker.streamSphere.models.Account;
import com.nobroker.streamSphere.models.Profile;
import com.nobroker.streamSphere.repositories.AccountRepo;
import com.nobroker.streamSphere.repositories.ProfileRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfileServices {

    //        accountId we will get from by JWT token
    Long accountId = 1L;

    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ProfileMapper profileMapper;

    @Transactional
    public void remove(long profileId){
        profileRepo.deleteById(profileId);
    }

    @Transactional
    public void update(ProfileDTO profileDTO){
        Profile updatedProfile = profileMapper.toProfile(profileDTO);

        Profile dbProfile = profileRepo.getReferenceById(updatedProfile.getId());
        updatedProfile.setCreated(dbProfile.getCreated());

        profileRepo.save(updatedProfile);
    }

    @Transactional
    public ProfileDTO save(ProfileDTO profileDTO){

        Account account = accountRepo.getReferenceById(accountId);

        Profile mappedProfile = profileMapper.toProfile(profileDTO);
        mappedProfile.setAccount(account);
        mappedProfile.setCreated(LocalDateTime.now());

        Profile dbProfile = profileRepo.save(mappedProfile);

        ProfileDTO dbProfileDTO = profileMapper.toProfileDTO(dbProfile);
        return dbProfileDTO;
    }

    public List<ProfileDTO> getAll(){

        List<Profile> dbProfiles = profileRepo.findByAccountId(accountId);

        return dbProfiles
                .stream()
                .map(profiles -> profileMapper.toProfileDTO(profiles))
                .toList();
    }

}
