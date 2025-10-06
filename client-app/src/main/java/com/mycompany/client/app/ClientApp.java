/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.client.app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 * @author nami
 */
public class ClientApp {
    
    private final static String URL = "http://localhost:8080/training-app/api/jta/mytable";
    
    public static List<MyTable> getAll(Client client) {
        try {
            return client.target(URL)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<MyTable>>() {});
        } catch (Exception e) {
            System.out.println("ERROR <getAll>: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        
        try {
            List<MyTable> all = getAll(client);
            
            System.out.println("-- GET ALL MY TABLE --");
            for (MyTable mt : all) {
                System.out.println(mt);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            client.close();
        }
    }
}