/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rgb.training.app.boundary.viewbeans;
import com.rgb.training.app.data.model.Device;
import com.rgb.training.app.data.repository.DeviceJTARepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
/**
 *
 * @author nami
 */

//funcions: get, post, delete
//get: llista
//post(onj classe) i delete: msg de ok/error
/**
  {
    "brand": "Sony",
    "deviceId": 11,
*/
@Named 
@SessionScoped
public class IndexBean implements Serializable {

   
    private List<Device> devices;
    private String devicesJson;
    @Inject
    private DeviceJTARepository deviceRepo;

    
    public void loadDevices() {
        devices = deviceRepo.getAll();

        JsonbConfig config = new JsonbConfig()
                                .withFormatting(true); 

        Jsonb jsonb = JsonbBuilder.create(config);
        this.devicesJson = jsonb.toJson(devices);
    }

    public String getDevicesJson() {
        return devicesJson;
    }


    
    
    

   
}
