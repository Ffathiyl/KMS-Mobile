package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class ProdiModel implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nama")
    @Expose
    private String nama;

    private List<KKModel> kkModelList;

    public ProdiModel() {
        this.id = UUID.randomUUID().toString();
        this.nama = "";
    }

    public ProdiModel(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public ProdiModel(String id, String nama, List<KKModel> kkModelList) {
        this.id = id;
        this.nama = nama;
        this.kkModelList = kkModelList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<KKModel> getKkModelList() {
        return kkModelList;
    }

    public void setKkModelList(List<KKModel> kkModelList) {
        this.kkModelList = kkModelList;
    }
}
