package com.xelba.service;

import java.security.Key;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.xelba.authutil.AuthToken;
import com.xelba.authutil.Credentials;
import com.xelba.authutil.JSONCredentials;
import com.xelba.authutil.TokenType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Path("/token")
public class TokenService {

    private static final Logger LOGGER = LogManager.getLogger(TokenService.class);

    // Use this key instance to parse tokens created with it.
    // Using a different key will cause an exception as the token was created with this key.
    // That is why declaring it as private static final.
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private static final long TOKEN_LIFETIME = 28800000L;   // 8 hour token lifetime in milliseconds

    @POST
    @Path("/authenticate")
    @PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToken(String inpuString) {
        JSONCredentials inputCredentials = new Gson().fromJson(inpuString, JSONCredentials.class);

        if(!(Credentials.getCredMap().containsKey(inputCredentials.getUsername())
                && Credentials.getCredMap().get(inputCredentials.getUsername()).equals(inputCredentials.getPassword()))) {
            LOGGER.error("Authentication failed!");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        
        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ", "JWT");
        builder.claim("userId", inputCredentials.getUsername());

        Date creationTime = new Date();
        builder.claim("exp", creationTime.getTime() + TOKEN_LIFETIME);
        
        String token = builder.signWith(key).compact();
        TokenType tType = new TokenType();
        tType.setExpireAt(creationTime.getTime());
        tType.setToken(token);

        String output = new Gson().toJson(tType);
		return Response.status(200).entity(output).build();
    }

    public static AuthToken parseToken(String token) {
        Jws<Claims> jws;
        Claims claimsMap = null;
        try {
            jws = Jwts.parser().setSigningKey(TokenService.key).parseClaimsJws(token);
            claimsMap = jws.getBody();
        } catch(JwtException je) {
            LOGGER.error("Invalid token provided!", je);
            return null;
        } catch(IllegalArgumentException iae) {
            LOGGER.error("Null or empty token provided!", iae);
        } catch(Exception e) {
            LOGGER.error("Exception occured while trying to parse the provided token!", e);
            return null;
        }
        
        AuthToken  authTokenModel = new AuthToken();

        if(!claimsMap.isEmpty()) {
            String user = (String) claimsMap.get("userId");

            authTokenModel.setUserName(user);
            authTokenModel.setExp((Long) claimsMap.get("exp"));
            
            if(Credentials.getPermitMap().containsKey(user)) {
                authTokenModel.setPermissions(Credentials.getPermitMap().get(user));
            }
        }
        return authTokenModel;
    }
    
    // Demo API for POC purpose only.
    // Not performing any substantial auth action. So commenting it out.
    /*@POST
	@Path("/authorize")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	}*/
}