package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KKModel implements Serializable {

    @SerializedName("Key")
    @Expose
    private String Key;

    @SerializedName("No")
    @Expose
    private String No;

    @SerializedName("ID_KK")
    @Expose
    private String IDKK;

    @SerializedName("ID_Karyawan")
    @Expose
    private String IDKaryawan;

    @SerializedName("Nama_Kelompok_Keahlian")
    @Expose
    private String NamaKelompokKeahlian;

    @SerializedName("Prodi")
    @Expose
    private String Prodi;

    @SerializedName("Deskripsi")
    @Expose
    private String Deskripsi;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Count")
    @Expose
    private String Count;

    public KKModel(){
        Key = "";
        No = "";
        this.IDKK = "";
        this.IDKaryawan = "";
        NamaKelompokKeahlian = "";
        Prodi = "";
        Deskripsi = "";
        Status = "";
        Count = "";
    }

    public KKModel(String key, String no, String IDKK, String IDKaryawan, String namaKelompokKeahlian, String prodi, String deskripsi, String status, String count) {
        Key = key;
        No = no;
        this.IDKK = IDKK;
        this.IDKaryawan = IDKaryawan;
        NamaKelompokKeahlian = namaKelompokKeahlian;
        Prodi = prodi;
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

    public String getIDKK() {
        return IDKK;
    }

    public void setIDKK(String IDKK) {
        this.IDKK = IDKK;
    }

    public String getIDKaryawan() {
        return IDKaryawan;
    }

    public void setIDKaryawan(String IDKaryawan) {
        this.IDKaryawan = IDKaryawan;
    }

    public String getNamaKelompokKeahlian() {
        return NamaKelompokKeahlian;
    }

    public void setNamaKelompokKeahlian(String namaKelompokKeahlian) {
        NamaKelompokKeahlian = namaKelompokKeahlian;
    }

    public String getProdi() {
        return Prodi;
    }

    public void setProdi(String prodi) {
        Prodi = prodi;
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
