package com.example.chatservice.member.service;

import com.example.chatservice.member.Member;
import com.example.chatservice.member.Profile;
import com.example.chatservice.member.repository.MemberRepository;
import com.example.chatservice.member.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    // 프로필 조회
    public Profile getProfile(String account) {
        return profileRepository.findByMemberAccount(account)
                .orElseThrow(() -> new RuntimeException("프로필을 찾을 수 없습니다."));

    }

    // 프로필 생성 (account 사용)
    public Profile createProfile(String account, String nickname, String photo) {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        Profile profile = Profile.builder()
                .member(member)
                .nickname(nickname)
                .photo(photo)
                .build();

        return profileRepository.save(profile);
    }

//    // 프로필 수정 (account 사용)
//    public Profile updateProfile(String account, String nickname, String photo) {
//        Profile profile = ProfileRepository.findByAccount(account)
//                .orElseThrow(() -> new RuntimeException("프로필을 찾을 수 없습니다."));
//
//        profile.updateProfile(nickname, photo);
//        return profileRepository.save(profile);
//    }

    /*// 프로필 삭제 (account 사용)
    public void deleteProfile(String account) {
        Profile profile = ProfileRepository.findByMember_Account(account)
                .orElseThrow(() -> new RuntimeException("프로필을 찾을 수 없습니다."));
        profileRepository.delete(profile);
    }*/
}
