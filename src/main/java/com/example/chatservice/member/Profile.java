package com.example.chatservice.member;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String photo;

    @OneToOne
    @JoinColumn(name = "member_id") // Member 엔티티와의 관계를 매핑
    private Member member;

    public Profile(Member member) {
        this.member = member;
    }

}
