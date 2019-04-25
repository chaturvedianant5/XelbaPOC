package com.xelba.app;

import com.xelba.authutil.AuthFilter;
import com.xelba.authutil.PermissionFilter;

import org.glassfish.jersey.server.ResourceConfig;

public class RestDemoApplication extends ResourceConfig {

    public RestDemoApplication() {
        packages("com.xelba.service");
        register(AuthFilter.class);
        register(PermissionFilter.class);
    }
}