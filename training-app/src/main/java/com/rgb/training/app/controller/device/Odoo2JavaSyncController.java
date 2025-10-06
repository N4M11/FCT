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
public class Odoo2JavaSyncController {
    
    private OdooConnection odooConnection;
    private HashMap<String,Device> odooDevices = new HashMap<>();
    private HashMap<String,Device> javaDevices = new HashMap<>();
    private DeviceJTARepository deviceJTA;
    private OdooIntegrationController odooConnector = new OdooIntegrationController();

    public void setDeviceRepository(DeviceJTARepository deviceJTA) {
        this.deviceJTA = deviceJTA;
    }
   
    public Odoo2JavaSyncController(OdooConnection connection){
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
          
            
             
            for (Map.Entry<String, Device> entry : odooDevices.entrySet()) {
                //si odoodevice existeix a localdevices actualitzar localdevice
                String snumber=entry.getKey();
                Device odooDevice=entry.getValue();
                if(javaDevices.containsKey(snumber)){
                    Device localDevice=javaDevices.get(snumber);
                    
                    localDevice.setBrand(odooDevice.getBrand());
                    localDevice.setModel(odooDevice.getModel());
                    
                    deviceJTA.update(localDevice);
                }else{
                    odooDevice.setFromodoo(Boolean.TRUE);
                    deviceJTA.create(odooDevice);
                }
  
             }
            
            //borrar al local lo que no está a odoo
            //serlialn que no estigui a odoo
            //for(String snumber:localDevices.keySet()){
              //  if(!odooDevices.containsKey(snumber)){
                //    deviceJTA.delete(localDevices.get(snumber));
                //}
            //}
            
            //System.out.println("Sync completed");
             

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
       
        
   
    }
}
