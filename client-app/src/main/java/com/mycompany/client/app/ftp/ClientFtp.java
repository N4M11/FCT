/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client.app.ftp;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author nami
 */
public class ClientFtp {
    
    private String server;
    private int port;
    private String user;
    private String passwd;
    private FTPClient ftp;
    
    public ClientFtp(String server, int port, String user, String passwd, FTPClient ftp){
        this.server=server;
        this.port=port;
        this.user=user;
        this.passwd=passwd;
        this.ftp=ftp;
    }
    
    void open() throws IOException {

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Error connecting FTP Server");
        }

        ftp.login(user, passwd);
    }
    
    void close() throws IOException {
        ftp.disconnect();
    }
    
    
    
    
    
}
