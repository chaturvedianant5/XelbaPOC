package com.xelba.app;

import org.glassfish.jersey.server.ResourceConfig;

public class RestDemoApplication extends ResourceConfig {

    public RestDemoApplication() {
        packages("com.xelba.service");
    }
}