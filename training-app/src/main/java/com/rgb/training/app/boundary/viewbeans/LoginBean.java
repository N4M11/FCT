/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rgb.training.app.boundary.viewbeans;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
/**
 *
 * @author nami
 */
@Named 
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private boolean logged=false;
    

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin";

    public String login() {
        if (ADMIN_USER.equals(this.username) && ADMIN_PASS.equals(this.password)) {
            logged=true;
            return "index?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new jakarta.faces.application.FacesMessage(
                    jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                    "Login error",
                    null));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
    
     public void validate() {
        if (!this.logged) {
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUsername() { return this.username; }
    public boolean getlogin() { 
  
        return this.logged; 
    }
    public void setNotLogged(){
        this.logged=false;
    }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
}

