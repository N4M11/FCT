/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.front.end.app.boundary.viewbeans;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author nami
 */
@Named 
@SessionScoped
public class LoginBean implements Serializable {
    
    private static final long serialVersionUID = 1102025;
    private static final Logger LOGGER =
      LoggerFactory.getLogger(LoginBean.class);
    
    public static final String HOME_PAGE_REDIRECT ="index?faces-redirect=true";
    public static final String LOGOUT_PAGE_REDIRECT ="login?faces-redirect=true";

    private String username;
    private String password;
    private boolean logged=false;
    

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin";

    public String login() {
        if (ADMIN_USER.equals(this.username) && ADMIN_PASS.equals(this.password)) {
            
            LOGGER.info("login successful for '{}'", username);
            logged=true;
            return HOME_PAGE_REDIRECT;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Login error",
              "Invalid or unknown user."));
            return null;
        }
    }

    public String logout() {
        
        LOGGER.debug("invalidating session for '{}'", username);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return LOGOUT_PAGE_REDIRECT;
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
    public boolean islogged() { 
        
        return this.logged; 
    }
    
    public void setNotLogged(){
        this.logged=false;
    }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
    

      public String isLoggedInForwardHome() {
        if (islogged()) {
          return HOME_PAGE_REDIRECT;
        }

        return null;
      }
}

