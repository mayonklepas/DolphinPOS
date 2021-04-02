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
public class StrukDto {
    String namaBarang;
    double diskonDetail;
    double jumlahJual;
    double hargaJual;
    double diskonMaster;
    double tax;
    String kodeTransaksi;
    Date tanggalPenjualan;
    String user;

    public StrukDto(String namaBarang, double diskonDetail, double jumlahJual, double hargaJual, double diskonMaster, double tax, String kodeTransaksi, Date tanggalPenjualan, String user) {
        this.namaBarang = namaBarang;
        this.diskonDetail = diskonDetail;
        this.jumlahJual = jumlahJual;
        this.hargaJual = hargaJual;
        this.diskonMaster = diskonMaster;
        this.tax = tax;
        this.kodeTransaksi = kodeTransaksi;
        this.tanggalPenjualan = tanggalPenjualan;
        this.user = user;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public double getDiskonDetail() {
        return diskonDetail;
    }

    public void setDiskonDetail(double diskonDetail) {
        this.diskonDetail = diskonDetail;
    }

    public double getJumlahJual() {
        return jumlahJual;
    }

    public void setJumlahJual(double jumlahJual) {
        this.jumlahJual = jumlahJual;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public double getDiskonMaster() {
        return diskonMaster;
    }

    public void setDiskonMaster(double diskonMaster) {
        this.diskonMaster = diskonMaster;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public Date getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggalPenjualan(Date tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    
}
