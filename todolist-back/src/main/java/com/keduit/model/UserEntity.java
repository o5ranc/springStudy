package com.keduit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")}) // username unique=true 준 효과랑 같음
public class UserEntity {

    @Id
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(nullable = false)
    private String username;

    private String password;

    // basic, admin
    private String role;

    private String authProvidor;
}
