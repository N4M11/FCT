/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.client.app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nami
 */
public class ClientApp {
    
    private final static String URL = "http://localhost:8080/training-app/api/jta/mytable/";
    
    public static List<MyTable> getAll(Client client) {
        try {
            return client.target(URL)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<MyTable>>() {}); //GENRICTYPE pq get retorna colletion
        } catch (Exception e) {
            System.out.println("ERROR <getAll>: " + e.getMessage());
            return null;
        }
    }
    
    public static Response create(Client client, MyTable myTable) {
        try {
            return client.target(URL+"post")
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(myTable, MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            System.out.println("ERROR <create>: " + e.getMessage());
            return null;
        }
    }
    
    public static Response update(Client client, MyTable myTable) { //act
        try {
            return client.target(URL)
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(myTable, MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            System.out.println("ERROR <update>: " + e.getMessage());
            return null;
        }
    }
    
    public static Response delete(Client client, Long id) {
        try {
            return client.target(URL)
                        .path(String.valueOf(id)) 
                        .request(MediaType.APPLICATION_JSON)
                        .delete();
        } catch (Exception e) {
            System.out.println("ERROR <delete>: " + e.getMessage());
            return null;
        }
    }
    


    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        
        //String name, Date birthdate, String address, boolean enable
        MyTable mt1,mt2;
        Date date = null;
        Response r;    
        try {
                
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            date = sdf.parse("17/01/2022");
                
        } catch (ParseException e) { //date
                e.printStackTrace();
        }
        
        try {
            
            mt2 = new MyTable("Client TEMP",date,"Calle Principal 456",true);
            create(client,mt2);
            
            List<MyTable> all = getAll(client);
            
            System.out.println("-- GET ALL MY TABLE --");
            for (MyTable record : all) {
                System.out.println(record);
            }
            

        mt1 = new MyTable("Client",date,"Calle Principal 123",true);
        
        System.out.println("Creating mt...");
        r=create(client,mt1);
        System.out.println(r);
        
        
        System.out.println("Updating mt id= 7...");
        mt1.setId(7l);
        mt1.setAddress("Adress updated");
        r=update(client,mt1);
        System.out.println(r);
        
        System.out.println("Deleting mt id = 8...");
        mt1.setId(9l);
        r=delete(client,mt1.getId());
        System.out.println(r);
        
        all = getAll(client);
            
            System.out.println("-- GET ALL MY TABLE --");
            for (MyTable record : all) {
                System.out.println(record);
            }


       
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            client.close();
        }
    }
}