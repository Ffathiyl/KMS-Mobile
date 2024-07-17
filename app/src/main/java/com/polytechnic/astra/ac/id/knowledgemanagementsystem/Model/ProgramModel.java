package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProgramModel implements Serializable {
    @SerializedName("Key")
    @Expose
    private String Key;

    @SerializedName("No")
    @Expose
    private String No;

    @SerializedName("KKiD")
    @Expose
    private String KKID;

    @SerializedName("Nama_Program")
    @Expose
    private String NamaProgram;

    @SerializedName("Deskripsi")
    @Expose
    private String Deskripsi;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Count")
    @Expose
    private String Count;



    public ProgramModel() {
    }

    public ProgramModel(String key, String no, String KKID, String namaProgram, String deskripsi, String status, String count) {
        Key = key;
        No = no;
        this.KKID = KKID;
        NamaProgram = namaProgram;
        Deskripsi = deskripsi;
        Status = status;
        Count = count;
    }


    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getKKID() {
        return KKID;
    }

    public void setKKID(String KKID) {
        this.KKID = KKID;
    }

    public String getNamaProgram() {
        return NamaProgram;
    }

    public void setNamaProgram(String namaProgram) {
        NamaProgram = namaProgram;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
