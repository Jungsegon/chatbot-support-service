package com.example.chatservice.member.controller;

import com.example.chatservice.member.Profile;
import com.example.chatservice.member.dto.ProfileRequest;
import com.example.chatservice.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/profile/{memberId}")
    public ResponseEntity<Profile> createOrUpdateProfile(@PathVariable String account, @RequestBody ProfileRequest profileRequest) {
        Profile profile = profileService.UpdateProfile(account, profileRequest);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/profile/{memberId}")
    public ResponseEntity<Profile> getProfileByMemberId(@PathVariable Long memberId) {
        Profile profile = profileService.getProfileByMemberId(memberId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

}
