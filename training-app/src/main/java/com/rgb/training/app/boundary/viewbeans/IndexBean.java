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
import java.util.ArrayList;
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
    private static final long serialVersionUID = 2102025;

    private int selectedOption = 0; 
    private int option = 0;
    
    private Device webDevice = new Device();
    private Integer deleteId;

    private String message;
    public static boolean done;
    private List<Device> devices;

    @Inject
    private DeviceJTARepository deviceRepo;

    
     public void loadDevices() {
        this.devices = this.deviceRepo.getAll();
    }

    public List<Device> getDevices() {
        return this.devices;
    }
    
   
    
     public void createDevice() {
        try {
            deviceRepo.create(webDevice);
            message = "Device created";
            done = true;
            
        } catch (Exception ex) {
            message = "Error creating Device " + ex.getMessage();
            done = false;
        }
    }

    public void updateDevice() {
        try {
            deviceRepo.update(webDevice);
            message = "Device updated.";
            done = true;
            if (option == 1) loadDevices();
        } catch (Exception ex) {
            message = "Error: " + ex.getMessage();
            done = false;
        }
    }

    public void deleteDeviceAction() {
        try {
            deviceRepo.delete(deleteId);
            message = "Device deleted";
            done = true;
            if (option == 1) loadDevices();
        } catch (Exception ex) {
            message = "Error: " + ex.getMessage();
            done = false;
        }
    }


    public int getSelectedOption() { return selectedOption; }
    public void setSelectedOption(int selectedOption) { this.selectedOption = selectedOption; }

    public int getOption() { return option; }

    public Device getWebDevice() { return webDevice; }
    public void setWebDevice(Device formDevice) { this.webDevice = formDevice; }

    public Integer getDeleteId() { return deleteId; }
    public void setDeleteId(Integer deleteId) { this.deleteId = deleteId; }

    public String getMessage() { return message; }

   
}
