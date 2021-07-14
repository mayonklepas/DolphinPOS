/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import com.df.dolphinpos.entities.HutangPiutangEntity;
import com.df.dolphinpos.entities.PembayaranHutangPiutangEntity;
import java.util.List;

/**
 *
 * @author Minami
 */
public class MasterDetailHutangPiutangDTO {
    HutangPiutangEntity master;
    List<PembayaranHutangPiutangEntity> detail;

    public HutangPiutangEntity getMaster() {
        return master;
    }

    public void setMaster(HutangPiutangEntity master) {
        this.master = master;
    }

    public List<PembayaranHutangPiutangEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<PembayaranHutangPiutangEntity> detail) {
        this.detail = detail;
    }
    
    
}
