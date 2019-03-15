package com.xelba.service;

import java.security.Key;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Path("/token")
public class TokenService {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @POST
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response getToken(String tokenPayload) {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(tokenPayload);
        String sub = "";
        String scope = "";

        if(jsonTree.isJsonObject()) {
            JsonObject jsonObj = jsonTree.getAsJsonObject();
            sub = jsonObj.get("sub").getAsString();
            scope = jsonObj.get("scope").getAsString();
        }

        JwtBuilder builder = Jwts.builder();
        builder.setSubject(sub);
        builder.claim("scope", scope);
        
		String token = builder.signWith(key).compact();
		return Response.status(200).entity(token).build();
    }
    
    @POST
	@Path("/authorize")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.TEXT_HTML)
	public Response authorizeToken(String token) {
        Jws<Claims> jws;
        Claims claimsMap = null;
        try {
            jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            claimsMap = jws.getBody();
        } catch(JwtException je) {
            je.printStackTrace();
        }
        
        String sub = "defaultSub";
        String scope = "defaultScope";

        if(!claimsMap.isEmpty()) {
            sub = claimsMap.get("sub").toString();
            scope = claimsMap.get("scope").toString();
        }
        
        
        String tokenDetails = "Details in the token:"
                            + "\nsub: " + sub
                            + "\nscope: " + scope;
		return Response.status(200).entity(tokenDetails).build();
	}
}