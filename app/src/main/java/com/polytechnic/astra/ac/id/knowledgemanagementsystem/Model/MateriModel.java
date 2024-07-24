package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MateriModel implements Serializable {
    @SerializedName("Key")
    @Expose
    private String Key;

    @SerializedName("Kat_ID")
    @Expose
    private String Kat_ID;

    @SerializedName("isBookmark")
    @Expose
    private boolean isBookmark;

    @SerializedName("Kategori")
    @Expose
    private String Kategori;

    @SerializedName("Judul")
    @Expose
    private String JudulKK;

    @SerializedName("File_pdf")
    @Expose
    private String FilePdf;

    @SerializedName("File_video")
    @Expose
    private String FileVideo;

    @SerializedName("Pengenalan")
    @Expose
    private String Pengenalan;

    @SerializedName("Keterangan")
    @Expose
    private String Keterangan;

    @SerializedName("Kata_kunci")
    @Expose
    private String KataKunci;

    @SerializedName("Gambar")
    @Expose
    private String Gambar;

    @SerializedName("Sharing_pdf")
    @Expose
    private String SharingPdf;

    @SerializedName("Sharing_video")
    @Expose
    private String SharingVideo;

    @SerializedName("Uploader")
    @Expose
    private String Uploader;

    @SerializedName("Creadate")
    @Expose
    private String Creadate;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Count")
    @Expose
    private String Count;

    @SerializedName("Pretest")
    @Expose
    private String Pretest;

    @SerializedName("Posttest")
    @Expose
    private String Posttest;

    public MateriModel() {
    }

    public MateriModel(String key, String kategori, String judulKK, String filePdf, String fileVideo, String pengenalan, String keterangan, String kataKunci, String gambar, String sharingPdf, String sharingVideo, String uploader, String creadate, String status, String count, String pretest, String posttest) {
        Key = key;
        Kategori = kategori;
        JudulKK = judulKK;
        FilePdf = filePdf;
        FileVideo = fileVideo;
        Pengenalan = pengenalan;
        Keterangan = keterangan;
        KataKunci = kataKunci;
        Gambar = gambar;
        SharingPdf = sharingPdf;
        SharingVideo = sharingVideo;
        Uploader = uploader;
        Creadate = creadate;
        Status = status;
        Count = count;
        Pretest = pretest;
        Posttest = posttest;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }

    public String getJudulKK() {
        return JudulKK;
    }

    public void setJudulKK(String judulKK) {
        JudulKK = judulKK;
    }

    public String getFilePdf() {
        return FilePdf;
    }

    public void setFilePdf(String filePdf) {
        FilePdf = filePdf;
    }

    public String getFileVideo() {
        return FileVideo;
    }

    public void setFileVideo(String fileVideo) {
        FileVideo = fileVideo;
    }

    public String getPengenalan() {
        return Pengenalan;
    }

    public void setPengenalan(String pengenalan) {
        Pengenalan = pengenalan;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getKataKunci() {
        return KataKunci;
    }

    public void setKataKunci(String kataKunci) {
        KataKunci = kataKunci;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getSharingPdf() {
        return SharingPdf;
    }

    public void setSharingPdf(String sharingPdf) {
        SharingPdf = sharingPdf;
    }

    public String getSharingVideo() {
        return SharingVideo;
    }

    public void setSharingVideo(String sharingVideo) {
        SharingVideo = sharingVideo;
    }

    public String getUploader() {
        return Uploader;
    }

    public void setUploader(String uploader) {
        Uploader = uploader;
    }

    public String getCreadate() {
        return Creadate;
    }

    public void setCreadate(String creadate) {
        Creadate = creadate;
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

    public String getPretest() {
        return Pretest;
    }

    public void setPretest(String pretest) {
        Pretest = pretest;
    }

    public String getPosttest() {
        return Posttest;
    }

    public void setPosttest(String posttest) {
        Posttest = posttest;
    }

    public String getKat_ID() {
        return Kat_ID;
    }

    public void setKat_ID(String kat_ID) {
        Kat_ID = kat_ID;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }
}
