package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {

    @SerializedName("RoleID")
    @Expose
    private String RoleID;

    @SerializedName("Role")
    @Expose
    private String Role;

    @SerializedName("Nama")
    @Expose
    private String Nama;

    @SerializedName("kry_id") // Pastikan nama ini sesuai dengan nama field di API
    @Expose
    private String kryId;

    public LoginModel() {
        this.RoleID = "";
        this.Role = "";
        this.Nama = "";
        this.kryId = ""; // Inisialisasi dengan string kosong
    }

    // Getter dan Setter
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

    public String getKryId() {
        return kryId;
    }

    public void setKryId(String kryId) {
        this.kryId = kryId;
    }
}
