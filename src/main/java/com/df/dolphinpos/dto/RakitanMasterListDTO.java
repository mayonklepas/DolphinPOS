/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.dto;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author mulyadi
 */
public class RakitanMasterListDTO {

    UUID id;
    UUID idOutlet;
    Date tanggalPerakitan;
    String kodePerakitan;
    String namaPerakitan;
    String kodeBarang;
    String namaBarang;
    double jumlahBarang;

    public RakitanMasterListDTO(UUID id, UUID idOutlet, Date tanggalPerakitan, String kodePerakitan, String namaPerakitan, String kodeBarang, String namaBarang, double jumlahBarang) {
        this.id = id;
        this.idOutlet = idOutlet;
        this.tanggalPerakitan = tanggalPerakitan;
        this.kodePerakitan = kodePerakitan;
        this.namaPerakitan = namaPerakitan;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
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

    public Date getTanggalPerakitan() {
        return tanggalPerakitan;
    }

    public void setTanggalPerakitan(Date tanggalPerakitan) {
        this.tanggalPerakitan = tanggalPerakitan;
    }

    public String getKodePerakitan() {
        return kodePerakitan;
    }

    public void setKodePerakitan(String kodePerakitan) {
        this.kodePerakitan = kodePerakitan;
    }

    public String getNamaPerakitan() {
        return namaPerakitan;
    }

    public void setNamaPerakitan(String namaPerakitan) {
        this.namaPerakitan = namaPerakitan;
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
    
    
    
    
}
