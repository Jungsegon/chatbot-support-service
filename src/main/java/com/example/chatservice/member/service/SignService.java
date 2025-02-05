package com.example.chatservice.member.service;


import com.example.chatservice.member.Authority;
import com.example.chatservice.member.Member;
import com.example.chatservice.member.Profile;
import com.example.chatservice.member.dto.SignRequest;
import com.example.chatservice.member.dto.SignResponse;
import com.example.chatservice.member.repository.MemberRepository;
import com.example.chatservice.member.repository.ProfileRepository;
import com.example.chatservice.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SignResponse login(SignRequest request) throws Exception {
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        return SignResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .name(member.getName())
                .nickname(member.getNickname())
                .roles(member.getRoles())
                .token(jwtProvider.createToken(member.getAccount(), member.getRoles()))
                .build();

    }

    @Transactional
    public boolean register(SignRequest request) throws Exception {
        try {
            Member member = Member.builder()
                    .account(request.getAccount())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .name(request.getName())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            memberRepository.save(member);

            // 프로필 자동 생성
            Profile profile = Profile.builder()
                    .member(member)
                    .nickname(request.getNickname())
                    .photo(null) // 기본 프로필 사진 설정 가능
                    .build();
            profileService.createProfile(member.getAccount(), profile);
            profileRepository.save(profile);

        } catch (Exception e) {
            e.printStackTrace(); // 예외 원인을 로그로 출력
            throw e; // 예외 그대로 다시 던짐 (덮어쓰지 않음)
        }
        return true;
    }

    public SignResponse getMember(String account) throws Exception {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponse(member);
    }

}
