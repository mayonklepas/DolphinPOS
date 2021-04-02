/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.service;

import com.df.dolphinpos.dto.PembelianReportDTO;
import com.df.dolphinpos.dto.PenjualanReportDTO;
import com.df.dolphinpos.dto.StrukDto;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.CatatanEntity;
import com.df.dolphinpos.entities.KartuKontakEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.entities.PenjualanDetailEntity;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.CatatanRepository;
import com.df.dolphinpos.repositories.KartuKontakRepository;
import com.df.dolphinpos.repositories.PembelianMasterRepository;
import com.df.dolphinpos.repositories.PenjualanDetailRepository;
import com.df.dolphinpos.repositories.PenjualanMasterRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
    CatatanRepository catatanrepo;

    @Autowired
    KartuKontakRepository karturepo;

    @Autowired
    PenjualanMasterRepository penjualanrepo;

    @Autowired
    PembelianMasterRepository pembelianrepo;

    @Autowired
    PenjualanDetailRepository penjualanDetailRepo;

    @Autowired
    ResourceLoader resLoad;

    public byte[] barangReport(UUID idOutlet) throws FileNotFoundException, JRException, IOException {
        List<BarangEntity> entity = barangrepo.findBarang(idOutlet);
        Resource fileRes = resLoad.getResource("classpath:report/barang.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] catatanReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<CatatanEntity> entity = catatanrepo.findCatatan(idOutlet, tanggalDari, tanggalHingga);
        Resource fileRes = resLoad.getResource("classpath:report/catatan.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] kontakReport(UUID idOutlet, int tipe) throws FileNotFoundException, JRException, IOException {
        List<KartuKontakEntity> entity = karturepo.findKontak(idOutlet, tipe);
        Resource fileRes = resLoad.getResource("classpath:report/kartukontak.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
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

    public byte[] penjualanReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<PenjualanReportDTO> entity = penjualanrepo.findPenjualanReport(idOutlet, tanggalDari, tanggalHingga);
        Resource fileRes = resLoad.getResource("classpath:report/penjualan.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] penjualanDetailReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<PenjualanMasterEntity> entity = penjualanrepo.findPenjualanReportDetail(idOutlet, tanggalDari, tanggalHingga);
        Resource fileRes = resLoad.getResource("classpath:report/penjualan-detail.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] pembelianReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<PembelianReportDTO> entity = pembelianrepo.findPembelianReport(idOutlet, tanggalDari, tanggalHingga);
        Resource fileRes = resLoad.getResource("classpath:report/pembelian.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] pembelianDetailReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<PembelianMasterEntity> entity = pembelianrepo.findPembelianReportDetail(idOutlet, tanggalDari, tanggalHingga);
        Resource fileRes = resLoad.getResource("classpath:report/pembelian-detail.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] fakturPenjualan(UUID id, UUID idOutlet) throws FileNotFoundException, JRException, IOException {
        List<PenjualanMasterEntity> entity = penjualanrepo.findPenjualanFakturById(id, idOutlet);
        Resource fileRes = resLoad.getResource("classpath:report/penjualan-faktur.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] struk(UUID id, UUID idOutlet) throws FileNotFoundException, JRException, IOException {
        List<StrukDto> entity = penjualanrepo.findStrukPenjualan(id, idOutlet);
        Resource fileRes = resLoad.getResource("classpath:report/struk.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

}
