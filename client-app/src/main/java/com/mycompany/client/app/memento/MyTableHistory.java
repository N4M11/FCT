/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client.app.memento;

import java.util.Stack;

/**
 *
 * @author nami
 * Caretaker
 */
public class MyTableHistory {
    
    private Stack<Memento> history = new Stack<>(); //pila -> l'ultim que entra es el 1r que surt
    
    public void saveState(Memento memento){history.push(memento);}
    
    public Memento undo(){
        if(!history.isEmpty()){
            return history.pop();//retorna ultim estat guardat
        }
        return null;
    }
}
