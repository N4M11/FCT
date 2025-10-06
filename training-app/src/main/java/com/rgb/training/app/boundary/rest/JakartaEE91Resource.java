package com.rgb.training.app.boundary.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author LuisCarlosGonzalez
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
