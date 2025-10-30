/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.front.end.app.boundary.rest;

import com.mycompany.front.end.app.model.Device;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 * @author nami
 */
@Stateless
public class ServerConnection {
    
    private final static String URL = "http://localhost:8081/api/jta/device";
    
    public List<Device> getAll(Client client) {
        try {
            return client.target(URL)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Device>>() {}); //GENRICTYPE pq get retorna colletion
        } catch (Exception e) {
            System.out.println("ERROR <getAll>: " + e.getMessage());
            return null;
        }
    }
    
    
     public void create(Client client, Device device) {
        try {
             client.target(URL+"post")
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(device, MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            System.out.println("ERROR <create>: " + e.getMessage());
        }
    }
     
      public void update(Client client, Device device) { //act
        try {
            client.target(URL)
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(device, MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            System.out.println("ERROR <update>: " + e.getMessage());
        }
    }
    
    public void delete(Client client, Long id) {
        try {
           client.target(URL)
                        .path(String.valueOf(id)) 
                        .request(MediaType.APPLICATION_JSON)
                        .delete();
        } catch (Exception e) {
            System.out.println("ERROR <delete>: " + e.getMessage());
        }
    }
     
     
    
    
}
