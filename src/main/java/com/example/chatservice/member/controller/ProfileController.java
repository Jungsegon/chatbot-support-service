package com.example.chatservice.member.controller;

import com.example.chatservice.member.Profile;
import com.example.chatservice.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    // 🔹 프로필 조회
    @GetMapping("/{account}")
    public ResponseEntity<Profile> getProfile(@PathVariable String account) {
        Profile profile = profileService.getProfile(account);
        return ResponseEntity.ok(profile);
    }

    // 🔹 프로필 생성
    @PostMapping("/{account}")
    public ResponseEntity<Profile> createProfile(
            @PathVariable String account,
            @RequestParam String nickname,
            @RequestParam String photo) {

        Profile profile = profileService.createProfile(account, nickname, photo);
        return ResponseEntity.ok(profile);
    }

    /*//  프로필 수정
    @PutMapping("/{account}")
    public ResponseEntity<Profile> updateProfile(
            @PathVariable String account,
            @RequestParam String nickname,
            @RequestParam String photo) {

        Profile updatedProfile = profileService.updateProfile(account, nickname, photo);
        return ResponseEntity.ok(updatedProfile);
    }*/

  /*  // 프로필 삭제
    @DeleteMapping("/{account}")
    public ResponseEntity<Void> deleteProfile(@PathVariable String account) {
        profileService.deleteProfile(account);
        return ResponseEntity.noContent().build();
    }*/
}
