package com.xelba.service.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clientemp")
public class EmployeeClient {

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response createEmp(String empJson) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/XelbaMySQLRest/employee/create");
        Response response = target.request().post(Entity.json(empJson));
        String queryRespose = response.readEntity(String.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        String output = "Created entry with the following details:" + queryRespose;
        return Response.status(200).entity(output).build();
    }
}