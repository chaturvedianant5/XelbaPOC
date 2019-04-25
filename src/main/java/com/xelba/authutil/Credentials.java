package com.xelba.authutil;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class Credentials {

    private static final ConcurrentHashMap<String, String> credMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, HashSet<Permission>> permitMap = new ConcurrentHashMap<>();

    static {
        credMap.put("hr@domain.com", "12345");
        credMap.put("manager@domain.com", "12345");
        credMap.put("user1@domain.com", "12345");
        credMap.put("exitteam@domain.com", "12345");
        credMap.put("user2@domain.com", "12345");

        HashSet<Permission> hrPermissions = new HashSet<Permission>();
        hrPermissions.add(Permission.Employee_Create);
        hrPermissions.add(Permission.Employee_Update);
        permitMap.put("hr@domain.com", hrPermissions);

        HashSet<Permission> managerPermissions = new HashSet<Permission>();
        managerPermissions.add(Permission.Employee_Update);
        permitMap.put("manager@domain.com", managerPermissions);

        HashSet<Permission> exitTeamPermissions = new HashSet<Permission>();
        exitTeamPermissions.add(Permission.Employee_Delete);
        permitMap.put("exitteam@domain.com", exitTeamPermissions);
    }

    public static ConcurrentHashMap<String, String> getCredMap() {
        return credMap;
    }

    public static ConcurrentHashMap<String, HashSet<Permission>> getPermitMap() {
        return permitMap;
    }
}