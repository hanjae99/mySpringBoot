package com.keduit.entity;

import com.keduit.constant.Role;
import com.keduit.dto.MemberFormDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDTO memberFormDTO, PasswordEncoder passwordEncoder){
        Member member = new Member();
        // 비밀번호 암호화
        String password = passwordEncoder.encode(memberFormDTO.getPassword());

        member.setName(memberFormDTO.getName());
        member.setEmail(memberFormDTO.getEmail());
        member.setPassword(password);
        member.setAddress(memberFormDTO.getAddress());
        member.setRole(Role.USER);

        return member;
    }
}
