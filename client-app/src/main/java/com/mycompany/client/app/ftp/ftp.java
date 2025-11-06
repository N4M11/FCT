/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client.app.ftp;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author nami
 */
public class ftp {
    private static final String SERVER="localhost";
    private static final int PORT=2121;
    private static final String USER="nami";
    private static final String PASSWD="diakite00";

    
    
    public static void listFiles(FTPClient ftpClient, boolean onlyDirs) throws IOException {
        FTPFile[] files = ftpClient.listFiles();
        System.out.println("Directori actual:");

        for (FTPFile file : files) {
            if (onlyDirs && !file.isDirectory()) continue;
            if (!onlyDirs && !file.isFile() && !file.isDirectory()) continue; 

            System.out.println((file.isDirectory() ? "[DIR] " : "[FILE] ") + file.getName());
        }
    }
    
    public static void createFile(FTPClient ftpClient){
        
        
    }
    
    
    public static void main(String[] args) {
        
        FTPClient ftp=new FTPClient();
        
        ClientFtp ftp_client= new ClientFtp(SERVER,PORT,USER,PASSWD,ftp);
        
        try{
            ftp_client.open();
            listFiles(ftp,false);

        }catch (Exception e) {
            System.out.println(e.getMessage());
       

    
    
    
        }
    }

    
    
}
