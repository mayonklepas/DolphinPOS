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
public class PembelianReportV2DTO {

    Date tanggalPembelian;
    String pengguna;
    String kodeBarang;
    String namaBarang;
    String satuanBarang;
    double jumlahBeli;
    double hargaBeli;
    double disc;

    public PembelianReportV2DTO(Date tanggalPembelian, String pengguna, String kodeBarang, String namaBarang, String satuanBarang, double jumlahBeli, double hargaBeli, double disc) {
        this.tanggalPembelian = tanggalPembelian;
        this.pengguna = pengguna;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.satuanBarang = satuanBarang;
        this.jumlahBeli = jumlahBeli;
        this.hargaBeli = hargaBeli;
        this.disc = disc;
    }

    public Date getTanggalPembelian() {
        return tanggalPembelian;
    }

    public void setTanggalPembelian(Date tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
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

    public double getJumlahBeli() {
        return jumlahBeli;
    }

    public void setJumlahBeli(double jumlahBeli) {
        this.jumlahBeli = jumlahBeli;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public double getDisc() {
        return disc;
    }

    public void setDisc(double disc) {
        this.disc = disc;
    }
    
    

}
