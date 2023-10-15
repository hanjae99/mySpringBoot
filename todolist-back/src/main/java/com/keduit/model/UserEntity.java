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
@Table(name = "User", uniqueConstraints = {@UniqueConstraint(columnNames = "userName")})
public class UserEntity {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    private String role;

    private String authProvider;
}
