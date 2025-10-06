package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.common.odoo.connection.OdooConnection;
import com.rgb.training.app.controller.device.Odoo2JavaSyncController;
import com.rgb.training.app.data.repository.DeviceJTARepository;
import com.rgb.training.app.config.CustomConfig;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.io.File;

@Path("/backend/device/sync")   
public class DeviceSyncResource {
    
    public DeviceSyncResource(){
    
    }
    
    @Inject
    private DeviceJTARepository deviceJTA;

    @GET
    public Response sync() {
        
        try {
            
            CustomConfig.loadConfig(CustomConfig.PATH+File.separator+"data.txt");

            OdooConnection odooConnection = new OdooConnection(
                CustomConfig.get_odoo_port(),
                CustomConfig.get_db_name(),
                CustomConfig.get_user_id(),
                CustomConfig.get_pswd()
            );
            
            Odoo2JavaSyncController syncController = new Odoo2JavaSyncController(odooConnection);
            syncController.setDeviceRepository(deviceJTA);
            syncController.sync();
            
            
            
            return Response.ok("Odoo to Java Sync ok").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Odoo to Java Sync error: " + e.getMessage()).build();
        }
    }
    
   
}