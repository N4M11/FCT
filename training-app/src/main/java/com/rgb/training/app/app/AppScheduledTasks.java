package com.rgb.training.app.app;

import com.rgb.training.app.boundary.rest.DeviceSyncResource;
import com.rgb.training.app.boundary.rest.DeviceSyncResource2;
import jakarta.ejb.Schedule;
import jakarta.ejb.Schedules;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import java.time.LocalDateTime;
import jakarta.ws.rs.core.Response;


/**
 *
 * @author luiscarlosgonzalez
 * 
 */
@Startup
@Singleton
public class AppScheduledTasks {
    
   
    @Inject 
    Provider<DeviceSyncResource> seatProvider;
    @Inject
    Provider<DeviceSyncResource2> seatProvider2;


    
    @Schedule(second = "*/30", minute = "*", hour = "*")
    public void deviceSync() {
        try{
            DeviceSyncResource dsyncr=seatProvider.get();
            DeviceSyncResource2 dsyncr2=seatProvider2.get();
            
            Response response1 = dsyncr.sync();
            Response response2 = dsyncr2.sync();
            
            System.out.println("Sync 1: " + response1.getStatus() + " - " + response1.getEntity());
            System.out.println("Sync 2: " + response2.getStatus() + " - " + response2.getEntity());


            
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Cada 5 segundos:" + LocalDateTime.now());
    }
    

    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void automaticTimer() {
        System.out.println("Cada 5 segundos:" + LocalDateTime.now());
    }
    
    
    @Schedules({
        @Schedule(hour = "12"),
        @Schedule(hour = "20")
    })
    public void multipleSchedule() {
        System.out.println("Multiple segundos:" + LocalDateTime.now());
    }
}
