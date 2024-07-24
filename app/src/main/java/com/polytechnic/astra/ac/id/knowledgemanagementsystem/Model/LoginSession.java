package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model;

public class LoginSession {

    private static LoginSession instance;
    private LoginModel loginModel;

    private LoginSession() {}

    public static synchronized LoginSession getInstance() {
        if (instance == null) {
            instance = new LoginSession();
        }
        return instance;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }
}
