package com.xelba.service;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.xelba.authutil.Permission;
import com.xelba.authutil.PermitUser;
import com.xelba.dao.EmployeeDao;
import com.xelba.entity.Employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
@Path("/employee")
public class EmployeeService {

	private static final Logger LOGGER = LogManager.getLogger(EmployeeService.class);
 
	@GET
	@Path("/{param}")
	@PermitAll
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/readjson/{id}")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Response readJson(@PathParam("id") Integer id, String json) {
		String output = "Read the following JSON from request " + json;
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/create")
	@PermitUser(permission = Permission.Employee_Create)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response createEmp(String empJson) {
		Gson gson = new Gson();
		Employee emp = gson.fromJson(empJson, Employee.class);
		EmployeeDao dao = new EmployeeDao();
		dao.createEmp(emp);
		String output = "Created entry with the following details:" + emp.toString();
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/update/{id}")
	@PermitUser(permission = Permission.Employee_Update)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response updateEmp(@PathParam("id") Integer id, String empJson) {
		Gson gson = new Gson();
		Employee transientEmp = gson.fromJson(empJson, Employee.class);
		transientEmp.setEmpId(id);
		EmployeeDao dao = new EmployeeDao();
		dao.updateEmp(transientEmp);
		String output = transientEmp.toString();
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/fetch/{id}")
	@Produces(MediaType.TEXT_HTML)
	public Response fetchEmp(@PathParam("id") Integer id) {
		EmployeeDao dao = new EmployeeDao();
		Employee emp = dao.getEmp(id);
		if(emp == null) {
			LOGGER.error("Employee with id " + id + " not found!");
			return Response.status(404).entity("Employee with id " + id + " not found!").build();
		}
		String output = emp.toString();
		return Response.status(200).entity(output).build();
	}

	@DELETE
	@Path("/delete/{id}")
	@PermitUser(permission = Permission.Employee_Delete)
	@Produces(MediaType.TEXT_HTML)
	public Response deleteEmp(@PathParam("id") Integer id) {
		EmployeeDao dao = new EmployeeDao();
		Employee emp = dao.getEmp(id);
		dao.deleteEmp(id);
		String output = "Successfully removed: " + emp.toString();
		return Response.status(200).entity(output).build();
	}
 
}
