package com.example.spacebookingweb.Database.Entity;

public enum ERole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;

    ERole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNameWithoutPrefix() {
        return roleName;
    }

    public String getRoleNameWithPrefix() {
        return "ROLE_" + roleName;
    }
}
