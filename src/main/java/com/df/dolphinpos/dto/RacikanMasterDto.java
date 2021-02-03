/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Minami
 */
public class RacikanMasterDto {
    String kodeResep;
    String namaResep;
    Date dateCreated;

    public RacikanMasterDto(String kodeResep, String namaResep, Date dateCreated) {
        this.kodeResep = kodeResep;
        this.namaResep = namaResep;
        this.dateCreated = dateCreated;
    }

    public String getKodeResep() {
        return kodeResep;
    }

    public void setKodeResep(String kodeResep) {
        this.kodeResep = kodeResep;
    }

    public String getNamaResep() {
        return namaResep;
    }

    public void setNamaResep(String namaResep) {
        this.namaResep = namaResep;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    
    
    
    
}
