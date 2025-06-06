package com.nobroker.streamSphere.controllers;

import com.nobroker.streamSphere.dtos.AuthResponseDTO;
import com.nobroker.streamSphere.dtos.ProfileDTO;
import com.nobroker.streamSphere.security.JwtUtil;
import com.nobroker.streamSphere.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileServices;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO profileDTO){
        return ResponseEntity.ok().body(profileServices.save(profileDTO));
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(){
        return ResponseEntity.ok().body(profileServices.getAll());
    }

    // ✅ Updated to generate a new JWT with profileId + email
    @GetMapping("/{profileId}")
    public ResponseEntity<AuthResponseDTO> generateProfileToken(@PathVariable Long profileId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // This will validate ownership internally
        profileServices.getProfile(profileId);

        // Generate new JWT with email + profileId
        String newToken = jwtUtil.generateToken(email, profileId);

        return ResponseEntity.ok(new AuthResponseDTO(newToken));
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
