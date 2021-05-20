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
public class PenjualanReportDTO {
    Date tanggalPenjualan;
    String kodePenjualanMaster;
    String deskripsi;
    String namaAkunKeuangan;
    String namaCustomer;
    String namaPengguna;
    double disc;
    double tax;
    double total;

    public PenjualanReportDTO(Date tanggalPenjualan,String kodePenjualanMaster, String deskripsi, String namaAkunKeuangan, String namaCustomer, String namaPengguna, double disc, double tax, double total) {
        this.tanggalPenjualan = tanggalPenjualan;
        this.kodePenjualanMaster=kodePenjualanMaster;
        this.deskripsi = deskripsi;
        this.namaAkunKeuangan = namaAkunKeuangan;
        this.namaCustomer = namaCustomer;
        this.namaPengguna = namaPengguna;
        this.disc = disc;
        this.tax = tax;
        this.total = total;
    }

    public Date getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggal(Date tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

    public String getKodePenjualanMaster() {
        return kodePenjualanMaster;
    }

    public void setKodePenjualanMaster(String kodePenjualanMaster) {
        this.kodePenjualanMaster = kodePenjualanMaster;
    }
    
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNamaAkunKeuangan() {
        return namaAkunKeuangan;
    }

    public void setNamaAkunKeuangan(String namaAkunKeuangan) {
        this.namaAkunKeuangan = namaAkunKeuangan;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public double getDisc() {
        return disc;
    }

    public void setDisc(double disc) {
        this.disc = disc;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
