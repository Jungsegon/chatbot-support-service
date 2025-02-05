package com.example.chatservice.member.service;

import com.example.chatservice.member.Member;
import com.example.chatservice.member.Profile;
import com.example.chatservice.member.dto.ProfileRequest;
import com.example.chatservice.member.repository.MemberRepository;
import com.example.chatservice.member.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    public void createProfile(String account, Profile profile) {
        // 프로필을 생성할 때 회원과 관련된 정보를 연결합니다.
        profile.setMember(memberRepository.findByAccount(account)
                .orElseThrow(() -> new EntityNotFoundException("Member not found")));
        profileRepository.save(profile);
    }
    public Profile UpdateProfile(String account, ProfileRequest profileRequest) {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));


        Profile profile = profileRepository.findByMember(member)
                .orElse(new Profile());

        if (profileRequest.getNickname() != null) {
            profile.setNickname(profileRequest.getNickname());
        }
        if(profileRequest.getPhoto() !=null){
            profile.setPhoto(profileRequest.getPhoto());
        }
        // 회원과 연결
        profile.setMember(member);

        return profileRepository.save(profile);


        /*
        // 프로필 정보 업데이트
        profile.setNickname(profileRequest.getNickname());
        profile.setInstruction(profileRequest.getInstruction());
        profile.setRole(profileRequest.getRole());
        profile.setProjectExperience(profileRequest.isProjectExperience());
        profile.setPersonalLink(profileRequest.getPersonalLink());
        profile.setPhoto(profileRequest.getPhoto());
        profile.setMember(member);

        return profileRepository.save(profile);
        */
    }
    public Profile getProfileByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        return profileRepository.findByMember(member)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
    }
}
