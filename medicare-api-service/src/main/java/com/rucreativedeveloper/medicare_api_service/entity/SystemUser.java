package com.rucreativedeveloper.medicare_api_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity(name="system_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemUser {
    @Id
    @Column(name="user_id", length = 80)
    private String userId;

    @Column(name="username", length = 100, unique = true)
    private String username;// email

    @Column(length = 250, name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, columnDefinition = "DATETIME")
    private Date createdDate;

    @Column(name="is_account_non_expired", columnDefinition = "TINYINT")
    private boolean isAccountNonExpired;
    @Column(name="is_account_non_locked", columnDefinition = "TINYINT")
    private boolean isAccountNonLocked;
    @Column(name="is_credentials_non_expired", columnDefinition = "TINYINT")
    private boolean isCredentialsNonExpired;
    @Column(name="is_enable", columnDefinition = "TINYINT")
    private boolean isEnabled;

    @Column(length = 10, name = "otp")
    private String otp;

    @ManyToMany
    @JoinTable(name="user_role_detail",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<UserRole> roles;



}
