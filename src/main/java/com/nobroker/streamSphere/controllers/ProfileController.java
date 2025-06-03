package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.dtos.ProfileDTO;
import com.nobroker.streamSphere.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileServices;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok().body(profileServices.save(profileDTO));
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(){
        return ResponseEntity.ok().body(profileServices.getAll());
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> getAllProfiles(@PathVariable Long profileId){
        return ResponseEntity.ok().body(profileServices.getProfile(profileId));
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> updateProfile(@PathVariable Long profileId, @RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok().body(profileServices.updateProfileByProfileId(profileId,profileDTO));
    }

    @DeleteMapping("/{profileId}")
    public void deleteProfile(@PathVariable Long profileId){
        profileServices.deleteProfileById(profileId);
    }
}
