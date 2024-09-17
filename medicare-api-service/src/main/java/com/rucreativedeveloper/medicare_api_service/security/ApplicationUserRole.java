package com.rucreativedeveloper.medicare_api_service.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(
            ApplicationUserPermission.DRUG_READ,
            ApplicationUserPermission.DRUG_WRITE,
            ApplicationUserPermission.ORDER_READ,
            ApplicationUserPermission.ORDER_WRITE
    )),
    PHARMACIST(Sets.newHashSet(
            ApplicationUserPermission.ORDER_READ,
            ApplicationUserPermission.DRUG_READ,
            ApplicationUserPermission.DRUG_WRITE
    )),
    USER(Sets.newHashSet(
            ApplicationUserPermission.DRUG_READ,
            ApplicationUserPermission.ORDER_READ,
            ApplicationUserPermission.ORDER_WRITE
    ));


    private final Set<ApplicationUserPermission> applicationUserPermissions;


    ApplicationUserRole(Set<ApplicationUserPermission> applicationUserPermissions) {
        this.applicationUserPermissions = applicationUserPermissions;
    }

    public Set<ApplicationUserPermission> getApplicationUserPermissions() {
        return applicationUserPermissions;
    }

    public Set<SimpleGrantedAuthority> grantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getApplicationUserPermissions()
                .stream().map(permission ->
                        new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(
                new SimpleGrantedAuthority("ROLE_" + this.name())
        );
        return permissions;

    }

}
