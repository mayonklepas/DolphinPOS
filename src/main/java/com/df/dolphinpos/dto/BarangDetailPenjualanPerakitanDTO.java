/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.RakitanMasterEntity;
import java.util.List;

/**
 *
 * @author mulyadi
 */
public class BarangDetailPenjualanPerakitanDTO {
    String kodeBarang;
    String namaBarang;
    double jumlahBarang;
    String satuanBarang;
    double hargaBeli;
    double hargaJual;
    int tipeBarang;
    String keterangan;
    RakitanMasterEntity rakitan;
    MarginPenjualanDTO marginPenjualan;

    public BarangDetailPenjualanPerakitanDTO(String kodeBarang, String namaBarang, double jumlahBarang, String satuanBarang, double hargaBeli, double hargaJual, int tipeBarang, String keterangan, RakitanMasterEntity rakitan, MarginPenjualanDTO marginPenjualan) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
        this.satuanBarang = satuanBarang;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.tipeBarang = tipeBarang;
        this.keterangan = keterangan;
        this.rakitan = rakitan;
        this.marginPenjualan = marginPenjualan;
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

    public double getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(double jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getSatuanBarang() {
        return satuanBarang;
    }

    public void setSatuanBarang(String satuanBarang) {
        this.satuanBarang = satuanBarang;
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

    public int getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(int tipeBarang) {
        this.tipeBarang = tipeBarang;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public RakitanMasterEntity getRakitan() {
        return rakitan;
    }

    public void setRakitan(RakitanMasterEntity rakitan) {
        this.rakitan = rakitan;
    }

    public MarginPenjualanDTO getMarginPenjualan() {
        return marginPenjualan;
    }

    public void setMarginPenjualan(MarginPenjualanDTO marginPenjualan) {
        this.marginPenjualan = marginPenjualan;
    }

    
    
    
    
    
    
}
