/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rgb.training.app.boundary.viewbeans;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
/**
 *
 * @author nami
 */
@Named 
@RequestScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin";

    public String login() {
        if (ADMIN_USER.equals(this.username) && ADMIN_PASS.equals(this.password)) {
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

    // getters y setters
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
}

