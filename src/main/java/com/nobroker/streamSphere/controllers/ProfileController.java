package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.dtos.CreateProfileDTO;
import com.nobroker.streamSphere.dtos.CreateProfileResponseDTO;
import com.nobroker.streamSphere.dtos.FindAllProfilesDTO;
import com.nobroker.streamSphere.models.Profile;
import com.nobroker.streamSphere.services.ProfileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileServices profileServices;

    @PostMapping("")
    CreateProfileResponseDTO create(@RequestBody CreateProfileDTO createProfileDTO){
        return profileServices.save(createProfileDTO);
    }

    @GetMapping("")
    List<FindAllProfilesDTO> getAllProfiles(){
        return profileServices.getAll();
    }
}
