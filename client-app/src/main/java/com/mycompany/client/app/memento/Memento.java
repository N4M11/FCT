/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client.app.memento;

import com.mycompany.client.app.MyTable;

/**
 *
 * @author nami
 */
public class Memento {
    
    private final String savedAdress;
    
    public Memento(String adress){this.savedAdress=adress;}
    
    public String getSavedAdress(){return savedAdress;}
    
}
