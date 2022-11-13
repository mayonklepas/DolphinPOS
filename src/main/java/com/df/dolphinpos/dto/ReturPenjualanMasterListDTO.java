/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Minami
 */
public class ReturPenjualanMasterListDTO {
    UUID id;
    UUID idOutlet;
    Date tanggalReturPenjualan;
    String kodeReturPenjualanMaster;
    int status;
    String deskripsi;
    double totalBelanja;
    int isPosting;

    public ReturPenjualanMasterListDTO(UUID id, UUID idOutlet, Date tanggalReturPenjualan, String kodeReturPenjualanMaster, int status, String deskripsi, double totalBelanja, int isPosting) {
        this.id = id;
        this.idOutlet = idOutlet;
        this.tanggalReturPenjualan = tanggalReturPenjualan;
        this.kodeReturPenjualanMaster = kodeReturPenjualanMaster;
        this.status = status;
        this.deskripsi = deskripsi;
        this.totalBelanja = totalBelanja;
        this.isPosting = isPosting;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdOutlet() {
        return idOutlet;
    }

    public void setIdOutlet(UUID idOutlet) {
        this.idOutlet = idOutlet;
    }

    public Date getTanggalReturPenjualan() {
        return tanggalReturPenjualan;
    }

    public void setTanggalReturPenjualan(Date tanggalReturPenjualan) {
        this.tanggalReturPenjualan = tanggalReturPenjualan;
    }

    public String getKodeReturPenjualanMaster() {
        return kodeReturPenjualanMaster;
    }

    public void setKodeReturPenjualanMaster(String kodeReturPenjualanMaster) {
        this.kodeReturPenjualanMaster = kodeReturPenjualanMaster;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(double totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public int getIsPosting() {
        return isPosting;
    }

    public void setIsPosting(int isPosting) {
        this.isPosting = isPosting;
    }

    

    
    
}
