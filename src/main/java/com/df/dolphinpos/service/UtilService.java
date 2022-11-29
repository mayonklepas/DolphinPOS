/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.service;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.imageio.ImageIO;
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

    public String getNoInvoice(UUID idOutlet, String table) {
        String invoice = "";
        String field = "";
        String prefix = "";
        if (table.equals("pembelian_master")) {
            field = "kode_pembelian_master";
            prefix = "PB";
        } else if (table.equals("penjualan_master")) {
            field = "kode_penjualan_master";
            prefix = "PJ";
        } else if (table.equals("retur_penjualan_master")) {
            field = "kode_retur_penjualan_master";
            prefix = "RJ";
        } else if (table.equals("retur_pembelian_master")) {
            field = "kode_retur_pembelian_master";
            prefix = "RB";
        } else if (table.equals("hutang")) {
            field = "kode";
            prefix = "HT";
        } else if (table.equals("piutang")) {
            field = "kode";
            prefix = "PT";
        } else if (table.equals("pembayaran_hutang")) {
            field = "kode_pembayaran";
            prefix = "PH";
        } else if (table.equals("pembayaran_piutang")) {
            field = "kode_pembayaran";
            prefix = "PP";
        } else if (table.equals("rakitan_master")) {
            field = "kode_perakitan";
            prefix = "PR";
        }

        String query = "SELECT " + field + " "
                + "FROM " + table + " "
                + "WHERE id_outlet=? ORDER BY date_created DESC";
        Query q = enma.createNativeQuery(query);
        q.setMaxResults(1);
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
            invoice = prefix + depan + belakang;
        } else {
            String sekarang = new SimpleDateFormat("yyMM").format(new Date());
            String rawinvoice = String.valueOf(dataObjct).substring(2);
            if (rawinvoice.substring(0, 4).equals(sekarang)) {
                long convertrawinvoice = Long.parseLong(rawinvoice) + 1;
                invoice = prefix + String.valueOf(convertrawinvoice);
            } else {
                String depan = new SimpleDateFormat("yyMM").format(new Date());
                String belakang = "000001";
                invoice = prefix + depan + belakang;
            }

        }

        return invoice;
    }
    
    
    public byte[] resizeImage(byte[] source) throws IOException{
        InputStream is=new ByteArrayInputStream(source);
        BufferedImage bri=ImageIO.read(is);
        
        int oriWidth = bri.getWidth();
        int oriHeight = bri.getHeight();
        
        double ratio = 1.0 * oriWidth / oriHeight;
        
        
        int setWidth = (int) (500*ratio);
        int setHeight = (int) (500/ratio);
       
        
        Image resizeImage = bri.getScaledInstance(setWidth,setHeight,Image.SCALE_SMOOTH);
        BufferedImage resultImage= new BufferedImage(setWidth,setHeight, BufferedImage.TYPE_INT_RGB);
        resultImage.getGraphics().drawImage(resizeImage, 0, 0, null);
        
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(resultImage, "jpg", baos);
        byte[] result=baos.toByteArray();
        return result;
    }

}
