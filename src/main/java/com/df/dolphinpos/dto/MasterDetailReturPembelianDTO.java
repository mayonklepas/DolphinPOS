/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.entities.ReturPembelianDetailEntity;
import com.df.dolphinpos.entities.ReturPembelianMasterEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class MasterDetailReturPembelianDTO {
    ReturPembelianMasterEntity master;
    List<ReturPembelianDetailEntity> detail;

    public ReturPembelianMasterEntity getMaster() {
        return master;
    }

    public void setMaster(ReturPembelianMasterEntity master) {
        this.master = master;
    }

    public List<ReturPembelianDetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<ReturPembelianDetailEntity> detail) {
        this.detail = detail;
    }
    
    

    
    
}
