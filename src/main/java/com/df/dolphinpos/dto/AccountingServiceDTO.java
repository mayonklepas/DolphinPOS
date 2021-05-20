/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import java.util.UUID;

/**
 *
 * @author Minami
 */
public class AccountingServiceDTO {
    String akun;
    String akun_lawan;
    double nominal;

    public String getAkun() {
        return akun;
    }

    public void setAkun(String akun) {
        this.akun = akun;
    }

    public String getAkun_lawan() {
        return akun_lawan;
    }

    public void setAkun_lawan(String akun_lawan) {
        this.akun_lawan = akun_lawan;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }
    
    

}
