package com.example.chatservice.member.repository;


import com.example.chatservice.member.Member;
import com.example.chatservice.member.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ProfileRepository extends JpaRepository<Profile, Long> {

        Optional<Profile> findByMember(Member member);


}
