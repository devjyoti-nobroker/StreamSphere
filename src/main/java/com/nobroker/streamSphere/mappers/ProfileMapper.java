package com.nobroker.streamSphere.mappers;

import com.nobroker.streamSphere.dtos.ProfileDTO;
import com.nobroker.streamSphere.models.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProfileMapper {

    public Profile toProfile(ProfileDTO profileDTO){
        Profile profile = new Profile();
        profile.setName(profileDTO.getName());
        profile.setAdult(profileDTO.isAdult());

        return profile;
    }

    public ProfileDTO toProfileDTO(Profile profile){
        ProfileDTO createProfileResponse = new ProfileDTO();
        createProfileResponse.setAdult(profile.isAdult());
        createProfileResponse.setId(profile.getId());
        createProfileResponse.setName(profile.getName());

        return createProfileResponse;
    }

}
