/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.JurnalUmumDetailEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class BukuBesarDTO {

    String kodeAkun;
    String namaAkun;
    Double openingBalance;
    List<JurnalUmumDetailEntity> detailJurnal;

    public String getKodeAkun() {
        return kodeAkun;
    }

    public void setKodeAkun(String kodeAkun) {
        this.kodeAkun = kodeAkun;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public List<JurnalUmumDetailEntity> getDetailJurnal() {
        return detailJurnal;
    }

    public void setDetailJurnal(List<JurnalUmumDetailEntity> detailJurnal) {
        this.detailJurnal = detailJurnal;
    }

}
