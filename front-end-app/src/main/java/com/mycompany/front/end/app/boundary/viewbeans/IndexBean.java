/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.front.end.app.boundary.viewbeans;
import com.mycompany.front.end.app.boundary.rest.ServerConnection;
import com.mycompany.front.end.app.model.Device;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
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
@Named 
@SessionScoped
public class IndexBean implements Serializable {
    private static final long serialVersionUID = 2102025;

    private int selectedOption = 0; 
    private int option = 0;
    
    private Device webDevice = new Device();
    private Long deleteId;

    private String message;
    public static boolean done;
    private List<Device> devices;
    
    Client client = ClientBuilder.newClient();


    @Inject
    private ServerConnection deviceRepo;

    
     public void loadDevices() {
        this.devices = this.deviceRepo.getAll(client);
    }

    public List<Device> getDevices() {
        return this.devices;
    }
      
    
     public void createDevice() {
        try {
            deviceRepo.create(client,webDevice);
             FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Device created", null));
        
        } catch (Exception ex) {
             FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Error creating Device " + ex.getMessage(), null));
        }
    }

    public void updateDevice() {
        try {
            deviceRepo.update(client,webDevice);
            FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Device updated", null));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error: " + ex.getMessage(), null));
   
        }
    }

    public void deleteDevice() {
        try {
            deviceRepo.delete(client,deleteId);
             FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Device deleted", null));
            
        } catch (Exception ex) {
             FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error: " + ex.getMessage(), null));
    
        }
    }
    
    public void prepareEdit(Device d) {
        this.webDevice = d;
        this.selectedOption = 3;
    }

    public int getSelectedOption() { return selectedOption; }
    public void setSelectedOption(int selectedOption) { this.selectedOption = selectedOption; }

    public int getOption() { return option; }

    public Device getWebDevice() { return webDevice; }
    public void setWebDevice(Device formDevice) { this.webDevice = formDevice; }

    public Long getDeleteId() { return deleteId; }
    public void setDeleteId(Long deleteId) { this.deleteId = deleteId; }

    public String getMessage() { return message; }

   
}
