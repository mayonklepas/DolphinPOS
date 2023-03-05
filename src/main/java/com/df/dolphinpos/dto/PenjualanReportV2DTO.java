/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;


import java.util.Date;

/**
 *
 * @author Minami
 */
public class PenjualanReportV2DTO {
    Date tanggalPenjualan;
    String pengguna;
    String kodeBarang;
    String namaBarang;
    String satuanBarang;
    double jumlahJual;
    double hargaBeli;
    double hargaJual;
    double disc;

    public PenjualanReportV2DTO(Date tanggalPenjualan, String pengguna, String kodeBarang, String namaBarang, String satuanBarang, double jumlahJual, double hargaBeli, double hargaJual, double disc) {
        this.tanggalPenjualan = tanggalPenjualan;
        this.pengguna = pengguna;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.satuanBarang = satuanBarang;
        this.jumlahJual = jumlahJual;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.disc = disc;
    }

    public Date getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggalPenjualan(Date tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

    public String getPengguna() {
        return pengguna;
    }

    public void setPengguna(String pengguna) {
        this.pengguna = pengguna;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getSatuanBarang() {
        return satuanBarang;
    }

    public void setSatuanBarang(String satuanBarang) {
        this.satuanBarang = satuanBarang;
    }

    public double getJumlahJual() {
        return jumlahJual;
    }

    public void setJumlahJual(double jumlahJual) {
        this.jumlahJual = jumlahJual;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public double getDisc() {
        return disc;
    }

    public void setDisc(double disc) {
        this.disc = disc;
    }
    
    
    
    
}
