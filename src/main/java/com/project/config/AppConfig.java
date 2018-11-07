package com.project.config;

import com.project.rest.AccountingController;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class AppConfig extends ResourceConfig {

    public AppConfig() {
        register(AccountingController.class);
        register(new ProjectBinder());
    }
}
