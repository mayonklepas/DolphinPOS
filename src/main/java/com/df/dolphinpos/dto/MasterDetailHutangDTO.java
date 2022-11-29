/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.HutangEntity;
import com.df.dolphinpos.entities.PembayaranHutangEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class MasterDetailHutangDTO {
    HutangEntity master;
    List<PembayaranHutangEntity> detail;

    public HutangEntity getMaster() {
        return master;
    }

    public void setMaster(HutangEntity master) {
        this.master = master;
    }

    public List<PembayaranHutangEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<PembayaranHutangEntity> detail) {
        this.detail = detail;
    }
    
    
}
