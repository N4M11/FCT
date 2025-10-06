/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.common.odoo.connection.OdooConnection;
import com.rgb.training.app.config.CustomConfig;
import com.rgb.training.app.controller.device.Java2OdooSyncController;
import com.rgb.training.app.data.repository.DeviceJTARepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author nami
 */
@Path("/backend/device/sync2")   
public class DeviceSyncResource2 {
    @Inject
    private DeviceJTARepository deviceJTA;
    
    /*
    /this.url=res[0];
                this.db_name=res[1];
                this.user_id=res[2];
                this.pswd=res[3];
    */

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
            
            
            Java2OdooSyncController syncController = new Java2OdooSyncController(odooConnection);
            syncController.setDeviceRepository(deviceJTA);
            syncController.sync();
            
            
            
            return Response.ok("Java to Odoo Sync ok").build();
        } catch (IOException e) {
            return Response.serverError().entity("Java to Odoo Sync error: " + e.getMessage()).build();
        }
    
    }
}
