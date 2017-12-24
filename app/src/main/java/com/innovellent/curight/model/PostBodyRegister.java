package com.innovellent.curight.model;

/**
 * Created by Mak on 12/18/2017.
 */

public class PostBodyRegister {

    private String name;
    private String mobile;
    private String email;
    private String password;
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PostBodyRegister(String name, String mobile, String email, String password, String role) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
