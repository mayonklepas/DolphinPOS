/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.JurnalUmumDetailEntity;
import com.df.dolphinpos.entities.JurnalUmumMasterEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class MasterDetailJurnalUmumDTO {
    JurnalUmumMasterEntity master;
    List<JurnalUmumDetailEntity> detail;

    public JurnalUmumMasterEntity getMaster() {
        return master;
    }

    public void setMaster(JurnalUmumMasterEntity master) {
        this.master = master;
    }

    public List<JurnalUmumDetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<JurnalUmumDetailEntity> detail) {
        this.detail = detail;
    }
    
    
}
