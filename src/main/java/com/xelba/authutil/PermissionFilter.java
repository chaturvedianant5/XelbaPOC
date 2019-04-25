package com.xelba.authutil;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.xelba.service.TokenService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Priority(Priorities.USER)
public class PermissionFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final Logger LOGGER = LogManager.getLogger(PermissionFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        Method method = resourceInfo.getResourceMethod();

        if(method.isAnnotationPresent(PermitUser.class)) {
            Permission permission = method.getAnnotation(PermitUser.class).permission();
            String headerToken = requestContext.getHeaders().get("X-Token").get(0);

            AuthToken authToken = TokenService.parseToken(headerToken);

            if(authToken == null) {
                requestContext.abortWith(Response
                        .status(Response.Status.FORBIDDEN).build());
                return;
            }

            if(!(isAccessAllowed(permission, authToken))) {
                requestContext.abortWith(Response
                        .status(Response.Status.FORBIDDEN).build());
                return;
            }
            LOGGER.info("We have got a Permission: " + permission.getAction().toString());
        }
    }

    public boolean isAccessAllowed(Permission permission, AuthToken authToken) {
        return authToken.getPermissions().stream()
                .anyMatch(userPermission -> userPermission.equals(permission));
    }
}