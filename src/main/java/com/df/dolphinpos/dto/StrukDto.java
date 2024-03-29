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
    double jumlahUang;
    double kembalian;
    String kodeTransaksi;
    Date tanggalPenjualan;
    String user;
    String namaKontak;
    String alamatKontak;
    String nohpKontak;
    int tipeAkunKeuangan;
    String keterangan;

    public StrukDto(String namaBarang, double diskonDetail, double jumlahJual, double hargaJual, double diskonMaster, double tax, double jumlahUang, double kembalian, String kodeTransaksi, Date tanggalPenjualan, String user, String namaKontak, String alamatKontak, String nohpKontak, int tipeAkunKeuangan, String keterangan) {
        this.namaBarang = namaBarang;
        this.diskonDetail = diskonDetail;
        this.jumlahJual = jumlahJual;
        this.hargaJual = hargaJual;
        this.diskonMaster = diskonMaster;
        this.tax = tax;
        this.jumlahUang = jumlahUang;
        this.kembalian = kembalian;
        this.kodeTransaksi = kodeTransaksi;
        this.tanggalPenjualan = tanggalPenjualan;
        this.user = user;
        this.namaKontak = namaKontak;
        this.alamatKontak = alamatKontak;
        this.nohpKontak = nohpKontak;
        this.tipeAkunKeuangan = tipeAkunKeuangan;
        this.keterangan = keterangan;
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

    public double getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(double jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public double getKembalian() {
        return kembalian;
    }

    public void setKembalian(double kembalian) {
        this.kembalian = kembalian;
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

    public String getNamaKontak() {
        return namaKontak;
    }

    public void setNamaKontak(String namaKontak) {
        this.namaKontak = namaKontak;
    }

    public String getAlamatKontak() {
        return alamatKontak;
    }

    public void setAlamatKontak(String alamatKontak) {
        this.alamatKontak = alamatKontak;
    }

    public String getNohpKontak() {
        return nohpKontak;
    }

    public void setNohpKontak(String nohpKontak) {
        this.nohpKontak = nohpKontak;
    }

    public int getTipeAkunKeuangan() {
        return tipeAkunKeuangan;
    }

    public void setTipeAkunKeuangan(int tipeAkunKeuangan) {
        this.tipeAkunKeuangan = tipeAkunKeuangan;
    }

    
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    

    
    
    
}
