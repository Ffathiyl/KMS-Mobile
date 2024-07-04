package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KategoriModel implements Serializable {
    @SerializedName("Key")
    @Expose
    private String Key;

    @SerializedName("No")
    @Expose
    private String No;

    @SerializedName("ProID")
    @Expose
    private String ProID;

    @SerializedName("Nama_Kategori")
    @Expose
    private String NamaKategori;

    @SerializedName("Deskripsi")
    @Expose
    private String Deskripsi;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Count")
    @Expose
    private String Count;

    public KategoriModel() {

    }

    public KategoriModel(String key, String no, String proID, String namaKategori, String deskripsi, String status, String count) {
        Key = key;
        No = no;
        ProID = proID;
        NamaKategori = namaKategori;
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

    public String getProID() {
        return ProID;
    }

    public void setProID(String proID) {
        ProID = proID;
    }

    public String getNamaKategori() {
        return NamaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        NamaKategori = namaKategori;
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
