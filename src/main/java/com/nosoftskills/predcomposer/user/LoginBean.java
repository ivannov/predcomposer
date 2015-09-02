package com.nosoftskills.predcomposer.user;

import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import static com.nosoftskills.predcomposer.user.PasswordHashUtil.hashPassword;

/**
 * @author Ivan St. Ivanov
 */
@Named("login")
@RequestScoped
public class LoginBean {

    @Inject
    private UserManagementService userManager;

    @Inject
    private UserContext userContext;

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
        User user = userManager.validateUser(userName, hashPassword(password));
        if (user != null) {
            userContext.setLoggedUser(user);
            return "/home";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong user name or password", "Provide correct user name and password"));
            return "/login";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login";
    }
}
