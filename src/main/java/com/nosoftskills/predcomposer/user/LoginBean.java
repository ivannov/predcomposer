package com.nosoftskills.predcomposer.user;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ivan St. Ivanov
 */
@Named("login")
@RequestScoped
public class LoginBean {

    @Inject
    private UserManagementService userManager;

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        if (userManager.validateUser(userName, password)) {
            return "home";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong user name or password", "Provide correct user name and password"));
            return "login";
        }
    }

    public String logout() {
        userManager.logout();
        return "login";
    }
}
