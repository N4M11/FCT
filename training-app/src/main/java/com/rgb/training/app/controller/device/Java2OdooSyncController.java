package com.rgb.training.app.controller.device;

import com.rgb.training.app.common.odoo.connection.OdooConnection;
import com.rgb.training.app.controller.odoo.OdooIntegrationController;
import com.rgb.training.app.data.model.Device;
import com.rgb.training.app.data.repository.DeviceJTARepository;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author luiscarlosgonzalez
 */
public class Java2OdooSyncController {
    
    private OdooConnection odooConnection;
    private HashMap<String,Device> odooDevices = new HashMap<>();
    private HashMap<String,Device> javaDevices = new HashMap<>();
    private DeviceJTARepository deviceJTA;
    private OdooIntegrationController odooConnector = new OdooIntegrationController();

    public void setDeviceRepository(DeviceJTARepository deviceJTA) {
        this.deviceJTA = deviceJTA;
    }
   
    public Java2OdooSyncController(OdooConnection connection){
        this.odooConnection = connection;
    }
    
    
    public void sync() {
       
      if (deviceJTA == null) {
            throw new IllegalStateException("DeviceRepository=null");
        }
        
        if (odooConnection == null) {
            throw new IllegalStateException("OdooConnection=null");
        }
        
        try {

            odooDevices=odooConnector.getOdooDevices(odooConnection);
            javaDevices=odooConnector.getJavaDevices(deviceJTA);  
            
        for (Map.Entry<String, Device> entry : javaDevices.entrySet()) {

                String snumber=entry.getKey();
                Device javaDevice=entry.getValue();
                if(odooDevices.containsKey(snumber)){
                    Device d=odooDevices.get(snumber);
                    
                    d.setBrand(javaDevice.getBrand());
                    d.setModel(javaDevice.getModel());
                    
                    odooConnector.updateDevice(javaDevice);
                }else{
                    javaDevice.setFromodoo(Boolean.FALSE);
                    odooConnector.createDevice(javaDevice);
                }
  
             }    
        
            
            
         } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
