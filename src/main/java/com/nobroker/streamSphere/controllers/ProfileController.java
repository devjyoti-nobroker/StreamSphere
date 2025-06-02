package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.dtos.ProfileDTO;
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
    ProfileDTO create(@RequestBody ProfileDTO profileDTO){
        return profileServices.save(profileDTO);
    }

    @GetMapping("")
    List<ProfileDTO> getAllProfiles(){
        return profileServices.getAll();
    }
}
