package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("RoleID")
    @Expose
    private String RoleID;

    @SerializedName("Role")
    @Expose
    private String Role;

    @SerializedName("Nama")
    @Expose String Nama;

    public LoginModel() {
        this.RoleID = "";
        this.Role = "";
        this.Nama = "";
    }

    public String getRoleID() {
        return RoleID;
    }

    public void setRoleID(String roleID) {
        RoleID = roleID;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }
}
