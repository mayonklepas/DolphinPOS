/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;
;

import java.util.Date;

/**
 *
 * @author Minami
 */
public class PembelianReportDTO {
    Date tanggalPembelian;
    String kodePembelian;
    String deskripsi;
    String namaAkunHolder;
    String namaCustomer;
    String namaPengguna;
    double disc;
    double tax;
    double total;

    public PembelianReportDTO(Date tanggalPembelian,String kodePembelian, String deskripsi, String namaAkunHolder, String namaCustomer, String namaPengguna, double disc, double tax, double total) {
        this.tanggalPembelian = tanggalPembelian;
        this.kodePembelian = kodePembelian;
        this.deskripsi = deskripsi;
        this.namaAkunHolder = namaAkunHolder;
        this.namaCustomer = namaCustomer;
        this.namaPengguna = namaPengguna;
        this.disc = disc;
        this.tax = tax;
        this.total = total;
    }

    public Date getTanggalPembelian() {
        return tanggalPembelian;
    }

    public void setTanggal(Date tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    public String getKodePembelian() {
        return kodePembelian;
    }

    public void setKodePembelian(String kodePembelian) {
        this.kodePembelian = kodePembelian;
    }

    
    
    
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNamaAkunHolder() {
        return namaAkunHolder;
    }

    public void setNamaAkunHolder(String namaAkunHolder) {
        this.namaAkunHolder = namaAkunHolder;
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
