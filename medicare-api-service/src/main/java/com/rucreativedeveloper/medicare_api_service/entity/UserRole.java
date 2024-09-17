package com.rucreativedeveloper.medicare_api_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name="user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {
    @Id
    @Column(name="role_id", length = 80)
    private String roleId;

    @Column(name="role_name", length = 80, unique = true)
    private String roleName;

}
