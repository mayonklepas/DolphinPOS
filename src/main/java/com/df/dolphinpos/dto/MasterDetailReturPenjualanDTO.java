/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.PenjualanDetailEntity;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
import com.df.dolphinpos.entities.ReturPenjualanDetailEntity;
import com.df.dolphinpos.entities.ReturPenjualanMasterEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class MasterDetailReturPenjualanDTO {
    ReturPenjualanMasterEntity master;
    List<ReturPenjualanDetailEntity> detail;

    public ReturPenjualanMasterEntity getMaster() {
        return master;
    }

    public void setMaster(ReturPenjualanMasterEntity master) {
        this.master = master;
    }

    public List<ReturPenjualanDetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<ReturPenjualanDetailEntity> detail) {
        this.detail = detail;
    }

    
    
}
