package com.xelba.service.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
        WebTarget target = client.target("http://localhost:8080/XelbaMySQLRest/rest/employee/create");
        Response response = target.request().post(Entity.json(empJson));
        String queryRespose = response.readEntity(String.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return Response.status(200).entity(queryRespose).build();
    }

    @GET
    @Path("/fetch/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response fetchEmp(@PathParam ("id") Integer id) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/XelbaMySQLRest/rest/employee/fetch/" + id);
        Response response = target.request().get();
        String queryRespose = response.readEntity(String.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return Response.status(200).entity(queryRespose).build();
    }

    @POST
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response updateEmp(@PathParam ("id") Integer id, String empJson) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/XelbaMySQLRest/rest/employee/update/" + id);
        Response response = target.request().post(Entity.json(empJson));
        String queryRespose = response.readEntity(String.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return Response.status(200).entity(queryRespose).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteEmp(@PathParam("id") Integer id) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/XelbaMySQLRest/rest/employee/delete/" + id);
        Response response = target.request().delete();
        String queryRespose = response.readEntity(String.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return Response.status(200).entity(queryRespose).build();
    }
}