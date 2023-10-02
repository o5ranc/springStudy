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
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.AUTO
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING) // 순서 변경되면 문제발생하므로, EnumType.STRING 권장
    private Role role;

    public static Member createMember(MemberFormDTO memberFormDTO,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDTO.getName());
        member.setEmail(memberFormDTO.getEmail());
        member.setAddress(memberFormDTO.getAddress());
        // 비밀번호를 암호화한 값을 저장
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);
		member.setRole(Role.ADMIN);
        return member;
    }
}
