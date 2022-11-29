/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.entities.RakitanDetailEntity;
import com.df.dolphinpos.entities.RakitanMasterEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class MasterDetailRakitanDTO {
    RakitanMasterEntity master;
    List<RakitanDetailEntity> detail;

    public RakitanMasterEntity getMaster() {
        return master;
    }

    public void setMaster(RakitanMasterEntity master) {
        this.master = master;
    }

    public List<RakitanDetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<RakitanDetailEntity> detail) {
        this.detail = detail;
    }

    
    
    
}
