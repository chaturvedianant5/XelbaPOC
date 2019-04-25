package com.xelba.authutil;

import java.util.HashSet;

public class AuthToken {
    private String userName;
    private HashSet<Permission> permissions;
    private Long exp;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the permissions
     */
    public HashSet<Permission> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(HashSet<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the exp
     */
    public Long getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Long exp) {
        this.exp = exp;
    }
}