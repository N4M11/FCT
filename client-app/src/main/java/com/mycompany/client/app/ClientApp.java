/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.client.app;
import com.rgb.training.app.data.model.Reparation;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
/**
 *
 * @author nami
 */

public class ClientApp {

    public static void main(String[] args) {
        
       Client client = ClientBuilder.newClient();
       
        Reparation name = client.target("http://example.com/webapi/hello")
            .request(MediaType.APPLICATION_JSON)
        .get(Reparation.class);
        
    }
}
