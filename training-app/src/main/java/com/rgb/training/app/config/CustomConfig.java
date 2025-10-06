package com.rgb.training.app.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Scanner;

/**
 *
 * @author LuisCarlosGonzalez
 */
public class CustomConfig {
    
    public static String ODOO_URL = "http://localhost:"; //0
    /*
    public static String ODOO_DB_NAME = "odoo16"; //1
    public static String ODOO_USER_ID = "2"; //2
    public static String ODOO_PASSWORD = "admin"; //3
    */
    public static String PATH=File.separator+"home"+File.separator+"nami"+File.separator+"Descargas"+File.separator+"training-app-250617"+
                            File.separator+"training-app"+File.separator+"src"+File.separator+"main"+File.separator+"java";
    
    public static String url; //0
    public static String db_name; //1
    public static String user_id; //2
    public static String pswd; //3
    
    private static FileTime lastKnownTime = null;
    
    //home/nami/Descargas/training-app-250617/training-app/src/main/java/data.txt
    //ruta config de glassfish , clase static
    
    public CustomConfig(){
    }
    
    public static boolean configChanged(String filename) throws IOException {
        Path path = Paths.get(filename);
        FileTime currentTime = Files.getLastModifiedTime(path);
        
        if (lastKnownTime == null) {
            lastKnownTime = currentTime;
            return false;
        }
        
        boolean changed = currentTime.compareTo(lastKnownTime) > 0;
        
        if (changed) {
            lastKnownTime = currentTime;
             System.out.println("data.txt modified");
        }
        
        return changed;
    }

    
    public static void loadConfig(String filename) throws IOException {

        if (user_id == null || configChanged(filename)) {
            
            
            String[] res;
            String config;
            String port;

            File file = new File(filename);

            try (FileReader freader = new FileReader(file);
                 BufferedReader breader = new BufferedReader(freader);
                 Scanner sreader = new Scanner(breader)) {

                sreader.nextLine();
                config = sreader.nextLine();

                res = config.split(",");
                port = res[0];
                res[0] = ODOO_URL + port;
                
                url = res[0];
                db_name = res[1];
                user_id = res[2];
                pswd = res[3];

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //#ODOO_PORT, ODOO_DB_NAME, ODOO_USER_ID, ODOO_PASSWORD
    //cridar loadconfig als getters

    public static String get_odoo_port(){
        return url;
    }
    
    public static String get_db_name(){
        return db_name;
    }
    public static String get_user_id(){
        return user_id;
    }
    public static String get_pswd(){
        return pswd;
    }
   
    
   
    
    

}
