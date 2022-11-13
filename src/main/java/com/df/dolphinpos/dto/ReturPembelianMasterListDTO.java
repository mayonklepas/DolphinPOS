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
public class ReturPembelianMasterListDTO {
    UUID id;
    UUID idOutlet;
    Date tanggalReturPembelian;
    String kodeReturPembelianMaster;
    int status;
    String deskripsi;
    double totalBelanja;
    int isPosting;

    public ReturPembelianMasterListDTO(UUID id, UUID idOutlet, Date tanggalReturPembelian, String kodeReturPembelianMaster, int status, String deskripsi, double totalBelanja, int isPosting) {
        this.id = id;
        this.idOutlet = idOutlet;
        this.tanggalReturPembelian = tanggalReturPembelian;
        this.kodeReturPembelianMaster = kodeReturPembelianMaster;
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

    public Date getTanggalReturPembelian() {
        return tanggalReturPembelian;
    }

    public void setTanggalReturPembelian(Date tanggalReturPembelian) {
        this.tanggalReturPembelian = tanggalReturPembelian;
    }

    public String getKodeReturPembelianMaster() {
        return kodeReturPembelianMaster;
    }

    public void setKodeReturPembelianMaster(String kodeReturPembelianMaster) {
        this.kodeReturPembelianMaster = kodeReturPembelianMaster;
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
