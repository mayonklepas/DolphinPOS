/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class MasterDetailPembelianDTO {
    PembelianMasterEntity master;
    List<PembelianDetailEntity> detail;


    public PembelianMasterEntity getMaster() {
        return master;
    }

    public void setMaster(PembelianMasterEntity master) {
        this.master = master;
    }

    public List<PembelianDetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<PembelianDetailEntity> detail) {
        this.detail = detail;
    }
    
    
}
