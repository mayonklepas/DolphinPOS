/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.PenjualanDetailEntity;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class MasterDetailPenjualanDTO {
    PenjualanMasterEntity master;
    List<PenjualanDetailEntity> detail;


    public PenjualanMasterEntity getMaster() {
        return master;
    }

    public void setMaster(PenjualanMasterEntity master) {
        this.master = master;
    }

    public List<PenjualanDetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<PenjualanDetailEntity> detail) {
        this.detail = detail;
    }
    
    
}
