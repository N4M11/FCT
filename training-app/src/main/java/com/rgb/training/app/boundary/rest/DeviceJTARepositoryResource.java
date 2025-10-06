package com.rgb.training.app.boundary.rest;
import com.rgb.training.app.data.model.Device;
import com.rgb.training.app.data.repository.DeviceJTARepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Client: http://localhost:8081/training-app/api/jta/device/
 *
 * @author LuisCarlosGonzalez
 */
@Path("jta/device")
@Produces(MediaType.APPLICATION_JSON)
//@SecurityKeyAuth
//@RestServiceLog
public class DeviceJTARepositoryResource {

    @Inject
    private DeviceJTARepository deviceRepo;

    @HEAD
    public Response headDefault() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    //@SecurityKeyAuth
    public Response get(@PathParam("id") Integer id) {
        Device result = deviceRepo.get(id);
        return Response.ok(result).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam(value = "offset") Integer offset, @QueryParam(value = "max-results") Integer maxResults) {
        List<Device> results = deviceRepo.getAll(offset, maxResults);
        return Response.ok(results).build();
    }

    @GET
    @Path("secured/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getWithSecurity(@Context HttpHeaders httpHeaders, @PathParam("id") Integer id) {
        boolean authorized = false;
        List<String> authHeaders = httpHeaders.getRequestHeader("Authorization");
        if (authHeaders == null || authHeaders.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        for (String auth : authHeaders) {
            if (auth.equals("1234")) {
                authorized = true;
            }
        }
        if (!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Device result = deviceRepo.get(id);
        return Response.ok(result).build();
    }

    @GET
    @Path("secured")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllWithSecurity(@Context HttpHeaders httpHeaders) {
        boolean authorized = false;
        List<String> authHeaders = httpHeaders.getRequestHeader("Authorization");
        if (authHeaders == null || authHeaders.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        for (String auth : authHeaders) {
            if (auth.equals("1234")) {
                authorized = true;
            }
        }
        if (!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        List<Device> results = deviceRepo.getAll();
        return Response.ok(results).build();
    }

    @POST
    @Path("/post")  
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDevice(Device newEntry) {
        try {
            Device created = deviceRepo.create(newEntry);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                   .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Device updatedEntry) {
        if (deviceRepo.get(updatedEntry.getDeviceId()) != null) {
            Device result = deviceRepo.update(updatedEntry);
            if (result != null) {
                return Response.ok(result).build();
            }
        }
        return Response.notModified().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        Integer deleted = deviceRepo.delete(id);
        if (deleted > 0) {
            return Response.ok(id).build();
        }
        return Response.notModified().build();
    }
}
