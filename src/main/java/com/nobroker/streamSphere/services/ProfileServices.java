package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.dtos.ProfileDTO;
import com.nobroker.streamSphere.exception.MaxProfileReachedException;
import com.nobroker.streamSphere.exception.ProfileNotFoundException;
import com.nobroker.streamSphere.exception.UnauthorizedProfileAccessException;
import com.nobroker.streamSphere.mappers.ProfileMapper;
import com.nobroker.streamSphere.models.Account;
import com.nobroker.streamSphere.models.Profile;
import com.nobroker.streamSphere.repositories.AccountRepo;
import com.nobroker.streamSphere.repositories.ProfileRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ProfileServices {

    //        accountId we will get from by JWT token
    Long accountId = 1L;

//    will be fetched from the db and cashed win the redis
    final int maxProfilePerUserLimit = 5;

    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ProfileMapper profileMapper;

    @Transactional
    public ProfileDTO save(ProfileDTO profileDTO){

        Long countProfilePerUser = profileRepo.countByAccountId(accountId);
        Account account = accountRepo.getReferenceById(accountId);

        if(countProfilePerUser == maxProfilePerUserLimit){
            throw new MaxProfileReachedException(account);
        }

        Profile mappedProfile = profileMapper.toProfile(profileDTO,account);
        Profile dbProfile = profileRepo.save(mappedProfile);

        ProfileDTO dbProfileDTO = profileMapper.toProfileDTO(dbProfile);
        return dbProfileDTO;
    }

    public ProfileDTO getProfile(Long profileId){
        Profile dbProfile = safelyGetProfile(profileId);

        if(!Objects.equals(dbProfile.getAccount().getId(), accountId)){
            throw new UnauthorizedProfileAccessException(profileId);
        }

        ProfileDTO profileDTO = profileMapper.toProfileDTO(dbProfile);

        return profileDTO;
    }

    @Transactional
    public ProfileDTO updateProfileByProfileId(Long profileId, ProfileDTO profileDTO){
        Profile dbProfile = safelyGetProfile(profileId);

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
        Profile dbProfile = safelyGetProfile(profileId);

        if(!Objects.equals(dbProfile.getAccount().getId(), accountId)){
            throw new UnauthorizedProfileAccessException(profileId);
        }
        profileRepo.deleteById(profileId);
    }

    private Profile safelyGetProfile(Long profileId){
        Profile dbProfile = profileRepo.findById(profileId)
                .orElseThrow(()-> new ProfileNotFoundException(profileId));
        return dbProfile;
    }

}
