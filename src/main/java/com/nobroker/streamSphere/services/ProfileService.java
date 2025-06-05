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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProfileService {

    //  accountId we will get from by JWT token


    //  will be fetched from the db and cashed win the redis
    final int maxProfilePerUserLimit = 5;

    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ProfileMapper profileMapper;


    //Get the email id from here
    private String getLoggedInEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    private Long getAccoutId(){
        return accountRepo.findByEmail(getLoggedInEmail())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .getId();
    }





    // save new Profile for the active Account
    @Transactional
    public ProfileDTO save(ProfileDTO profileDTO){
    // get number of profiles for the account
        Long countProfilePerUser = profileRepo.countByAccountId(getAccoutId());
        Account account = accountRepo.getReferenceById(getAccoutId());

        // implementing limit on the max number of profile per user
        if(countProfilePerUser >= maxProfilePerUserLimit){
            throw new MaxProfileReachedException(account);
        }

        // profileDTO -> profile via profileMapper then save
        Profile mappedProfile = profileMapper.toProfile(profileDTO,account);
        Profile dbProfile = profileRepo.save(mappedProfile);

        // Returning dbProfileDTO
        return profileMapper.toProfileDTO(dbProfile);
    }

    // Get Profile detail
    public ProfileDTO getProfile(Long profileId){
        Profile dbProfile = safelyGetProfile(profileId);

        // Ensuring that the profile belongs to the active Account
        validateProfileBelongToAccount(dbProfile,getAccoutId());
        // Returning dbProfileDTO
        return profileMapper.toProfileDTO(dbProfile);
    }

    // Updating Profile
    @Transactional
    public ProfileDTO updateProfileByProfileId(Long profileId, ProfileDTO profileDTO){
        Profile dbProfile = safelyGetProfile(profileId);

        // Updating only modified fields
        if(profileDTO.getName() != null)
            dbProfile.setName(profileDTO.getName());
        if(profileDTO.getAdult() != null)
            dbProfile.setAdult(profileDTO.getAdult());

        // saving the changes and returning dbProfileDTO
        profileRepo.save(dbProfile);
        return profileMapper.toProfileDTO(dbProfile);
    }

    public List<ProfileDTO> getAll(){

        String email = getLoggedInEmail();
        Long accountId = accountRepo.findByEmail(getLoggedInEmail())
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .getId();


        // All Profiles belonging to the current Account
        List<Profile> dbProfiles = profileRepo.findByAccountId(accountId);
        // convert Profile to ProfileDTO
        return dbProfiles
                .stream()
                .map(profileMapper::toProfileDTO)
                .toList();
    }

    @Transactional
    public void deleteProfileById(Long profileId){
        Profile dbProfile = safelyGetProfile(profileId);
        // Ensuring that the profile belongs to the active Account
        validateProfileBelongToAccount(dbProfile,getAccoutId());
        // deleting Profile
        profileRepo.deleteById(profileId);
    }

    private Profile safelyGetProfile(Long profileId){
        return profileRepo.findById(profileId)
                .orElseThrow(()-> new ProfileNotFoundException(profileId));
    }

    private void validateProfileBelongToAccount(Profile profile, Long accountId){
        if(!Objects.equals(profile.getAccount().getId(), accountId)){
            throw new UnauthorizedProfileAccessException(profile.getId());
        }
    }

}
