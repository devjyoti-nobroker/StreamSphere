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
    public ProfileDTO save(ProfileDTO profileDTO){

        Account account = accountRepo.getReferenceById(accountId);

        Profile mappedProfile = profileMapper.toProfile(profileDTO);
        mappedProfile.setAccount(account);
        mappedProfile.setCreated(LocalDateTime.now());

        Profile dbProfile = profileRepo.save(mappedProfile);

        ProfileDTO dbProfileDTO = profileMapper.toProfileDTO(dbProfile);
        return dbProfileDTO;
    }

    public ProfileDTO getProfile(Long profileId){
        Profile dbProfile = profileRepo.getReferenceById(profileId);

        ProfileDTO profileDTO = profileMapper.toProfileDTO(dbProfile);

        return profileDTO;
    }

    @Transactional
    public ProfileDTO updateProfileByProfileId(Long profileId, ProfileDTO profileDTO){
        Profile dbProfile = profileRepo.getReferenceById(profileId);
        if(profileDTO.getName() != null)
            dbProfile.setName(profileDTO.getName());
        if(profileDTO.getAdult() != null)
            dbProfile.setAdult(profileDTO.getAdult());

        profileRepo.save(dbProfile);

        ProfileDTO updatedProfileDTO = profileMapper.toProfileDTO(dbProfile);
        return updatedProfileDTO;
    }

    public List<ProfileDTO> getAll(){

        List<Profile> dbProfiles = profileRepo.findByAccountId(accountId);

        return dbProfiles
                .stream()
                .map(profiles -> profileMapper.toProfileDTO(profiles))
                .toList();
    }

    public void deleteProfileById(Long profileId){
        profileRepo.deleteById(profileId);
    }

}
