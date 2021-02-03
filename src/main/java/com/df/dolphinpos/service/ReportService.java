/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.service;

import com.df.dolphinpos.dto.PembelianReportDTO;
import com.df.dolphinpos.dto.PenjualanReportDTO;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.KartuKontakEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.KartuKontakRepository;
import com.df.dolphinpos.repositories.PembelianMasterRepository;
import com.df.dolphinpos.repositories.PenjualanMasterRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Minami
 */
@Service
public class ReportService {

    @Autowired
    BarangRepository barangrepo;

    @Autowired
    KartuKontakRepository karturepo;

    @Autowired
    PenjualanMasterRepository penjualanrepo;

    @Autowired
    PembelianMasterRepository pembelianrepo;

    public byte[] barangReport() throws FileNotFoundException, JRException {
        List<BarangEntity> entity = barangrepo.findBarang();
        File file = ResourceUtils.getFile("classpath:report/barang.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] kontakReport(int tipe) throws FileNotFoundException, JRException {
        List<KartuKontakEntity> entity = karturepo.findKontak(tipe);
        File file = ResourceUtils.getFile("classpath:report/kartukontak.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        String tipeKontakLabel = "";
        if (tipe == 0) {
            tipeKontakLabel = "Data Suplier";
        } else {
            tipeKontakLabel = "Data Customer";
        }
        map.put("tipeKontak", tipeKontakLabel);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] penjualanReport() throws FileNotFoundException, JRException {
        List<PenjualanReportDTO> entity = penjualanrepo.findPenjualanReport();
        File file = ResourceUtils.getFile("classpath:report/penjualan.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] penjualanDetailReport() throws FileNotFoundException, JRException {
        List<PenjualanMasterEntity> entity = penjualanrepo.findPenjualan();
        File file = ResourceUtils.getFile("classpath:report/penjualan-detail.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] pembelianReport() throws FileNotFoundException, JRException {
        List<PembelianReportDTO> entity = pembelianrepo.findPembelianReport();
        File file = ResourceUtils.getFile("classpath:report/pembelian.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }
    
    
        public byte[] pembelianDetailReport() throws FileNotFoundException, JRException {
        List<PembelianMasterEntity> entity = pembelianrepo.findPembelian();
        File file = ResourceUtils.getFile("classpath:report/pembelian-detail.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

}
