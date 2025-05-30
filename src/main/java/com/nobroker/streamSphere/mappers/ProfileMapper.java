package com.nobroker.streamSphere.mappers;

import com.nobroker.streamSphere.dtos.CreateProfileDTO;
import com.nobroker.streamSphere.dtos.CreateProfileResponseDTO;
import com.nobroker.streamSphere.dtos.FindAllProfilesDTO;
import com.nobroker.streamSphere.models.Profile;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProfileMapper {

    public Profile toProfile(CreateProfileDTO createProfileDTO){
        Profile profile = new Profile();

        profile.setCreated(LocalDateTime.now());
        profile.setLastUpdated(LocalDateTime.now());
        profile.setName(createProfileDTO.getName());
        profile.setAdult(createProfileDTO.isAdult());

        return profile;
    }

    public CreateProfileResponseDTO toCreateProfileResponse(Profile profile){
        CreateProfileResponseDTO createProfileResponse = new CreateProfileResponseDTO();

        createProfileResponse.setAdult(profile.isAdult());
        createProfileResponse.setCreated(profile.getCreated());
        createProfileResponse.setId(profile.getId());
        createProfileResponse.setName(profile.getName());

        return createProfileResponse;
    }

    public FindAllProfilesDTO toFindAllProfile(Profile profile){
        FindAllProfilesDTO findAllProfilesDTO = new FindAllProfilesDTO();

        findAllProfilesDTO.setAdult(profile.isAdult());
        findAllProfilesDTO.setId(profile.getId());
        findAllProfilesDTO.setName(profile.getName());

        return findAllProfilesDTO;
    }
}
