package com.rucreativedeveloper.medicare_api_service.security;

public enum ApplicationUserPermission {
    DRUG_READ("drug:read"),
    DRUG_WRITE("drug:write"),
    ORDER_READ("order:read"),
    ORDER_WRITE("order:write");

    private final String permission;


    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
