/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Minami
 */
@Service
public class UtilService {

    @Autowired
    EntityManager enma;

    public String getNoInvoice(UUID idOutlet) {
        String invoice = "";
        String query = "SELECT kode_penjualan_master "
                + "FROM penjualan_master "
                + "WHERE id_outlet=? ORDER BY date_created DESC LIMIT 1";
        Query q = enma.createNativeQuery(query);
        q.setParameter(1, idOutlet);
        Object dataObjct = null;
        try {
            dataObjct = q.getSingleResult();
        } catch (Exception e) {
            dataObjct = null;
            e.printStackTrace();
        }
        
        if (dataObjct == null) {
            String depan = new SimpleDateFormat("yyMM").format(new Date());
            String belakang = "000001";
            invoice = depan + belakang;
        } else {
            String sekarang = new SimpleDateFormat("yyMM").format(new Date());
            String rawinvoice = String.valueOf(dataObjct);
            if (rawinvoice.substring(0, 4).equals(sekarang)) {
                long convertrawinvoice = Long.parseLong(rawinvoice) + 1;
                invoice = String.valueOf(convertrawinvoice);
            } else {
                String depan = new SimpleDateFormat("yyMM").format(new Date());
                String belakang = "000001";
                invoice = depan + belakang;
            }

        }

        return invoice;
    }
    
}
