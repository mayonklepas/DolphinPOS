/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

/**
 *
 * @author Minami
 */
public class MarginPenjualanDTO {
    String kodeBarang;
    String namaBarang;
    String satuan;
    double hargaBeli;
    double totalDiskon;
    double totalHargaJual;
    double jumlah;

    public MarginPenjualanDTO(String kodeBarang, String namaBarang, String satuan, double hargaBeli,double totalDiskon, double totalHargaJual, double jumlah) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.satuan = satuan;
        this.hargaBeli = hargaBeli;
        this.totalDiskon = totalDiskon;
        this.totalHargaJual = totalHargaJual;
        this.jumlah = jumlah;
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

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public double getTotalDiskon() {
        return totalDiskon;
    }

    public void setTotalDiskon(double totalDiskon) {
        this.totalDiskon = totalDiskon;
    }
    
    

    public double getTotalHargaJual() {
        return totalHargaJual;
    }

    public void setTotalHargaJual(double totalHargaJual) {
        this.totalHargaJual = totalHargaJual;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

   
    

}
