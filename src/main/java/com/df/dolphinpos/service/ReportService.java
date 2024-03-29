/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.service;

import com.df.dolphinpos.dto.BarangDetailPenjualanPerakitanDTO;
import com.df.dolphinpos.dto.BukuBesarDTO;
import com.df.dolphinpos.dto.MarginPenjualanDTO;
import com.df.dolphinpos.dto.NeracaSaldoDTO;
import com.df.dolphinpos.dto.PembelianReportDTO;
import com.df.dolphinpos.dto.PembelianReportV2DTO;
import com.df.dolphinpos.dto.PenjualanReportDTO;
import com.df.dolphinpos.dto.PenjualanReportV2DTO;
import com.df.dolphinpos.dto.StrukDto;
import com.df.dolphinpos.entities.AkunKeuanganEntity;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.CatatanEntity;
import com.df.dolphinpos.entities.KartuKontakEntity;
import com.df.dolphinpos.entities.KoreksiStokEntity;
import com.df.dolphinpos.entities.OutletEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.entities.PenjualanDetailEntity;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
import com.df.dolphinpos.entities.RakitanMasterEntity;
import com.df.dolphinpos.repositories.AkunKeuanganRepository;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.CatatanRepository;
import com.df.dolphinpos.repositories.JurnalUmumDetailRepository;
import com.df.dolphinpos.repositories.KartuKontakRepository;
import com.df.dolphinpos.repositories.KoreksiStokRepository;
import com.df.dolphinpos.repositories.OutletRepository;
import com.df.dolphinpos.repositories.PembelianMasterRepository;
import com.df.dolphinpos.repositories.PenjualanDetailRepository;
import com.df.dolphinpos.repositories.PenjualanMasterRepository;
import com.df.dolphinpos.repositories.RakitanMasterRepository;
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
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    OutletRepository outletRepo;

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

    @Autowired
    AkunKeuanganRepository akunKeuanganRepo;

    @Autowired
    JurnalUmumDetailRepository jurnalDetailRepo;

    @Autowired
    KoreksiStokRepository koreksiStokRepo;

    @Autowired
    RakitanMasterRepository rakitanMasterRepo;

    @Autowired
    EntityManager enma;

    public Map getOutlet(UUID idOutlet) {
        OutletEntity outletEntity = outletRepo.findById(idOutlet).get();
        Map map = new HashMap();
        map.put("kode", outletEntity.getKodeOutlet());
        map.put("nama", outletEntity.getNamaOutlet());
        map.put("alamat", outletEntity.getAlamatOutlet());
        map.put("nohp", outletEntity.getNohpOutlet());
        return map;
    }

    public byte[] barangReport(UUID idOutlet) throws FileNotFoundException, JRException, IOException {
        List<BarangEntity> entity = barangrepo.findBarang(idOutlet);
        Resource fileRes = resLoad.getResource("classpath:report/barang.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] barangReportDetail(UUID idOutlet, Date tanggalDari, Date tanggalHingga, String kodeBarang, int segment) throws FileNotFoundException, JRException, IOException {
        List<BarangDetailPenjualanPerakitanDTO> entity = new ArrayList<>();

        List<BarangEntity> listBarangEntity = null;

        if (kodeBarang.equals("")) {
            listBarangEntity = barangrepo.findBarang(idOutlet);
        } else {
            listBarangEntity = barangrepo.findBarangByKode(idOutlet, kodeBarang);
        }

        for (BarangEntity barangEntity : listBarangEntity) {
            Optional<RakitanMasterEntity> optRakitanMaster = rakitanMasterRepo.findByIdOutletAndKodeBarangOrderByDateCreatedDesc(idOutlet, barangEntity.getKodeBarang());

            RakitanMasterEntity rakitanMaster = null;

            if (optRakitanMaster.isPresent()) {
                rakitanMaster = optRakitanMaster.get();
            }

            MarginPenjualanDTO marginPenjualan = null;

            Optional<MarginPenjualanDTO> optMarginPenjualan = penjualanDetailRepo.findMarginPenjualanByKodeBarang(idOutlet, barangEntity.getKodeBarang(), tanggalDari, tanggalHingga);

            if (optMarginPenjualan.isPresent()) {
                marginPenjualan = optMarginPenjualan.get();
            }

            if (segment == 1) {

                if (marginPenjualan != null) {
                    entity.add(new BarangDetailPenjualanPerakitanDTO(
                            barangEntity.getKodeBarang(),
                            barangEntity.getNamaBarang(),
                            barangEntity.getJumlahBarang(),
                            barangEntity.getSatuanBarang(),
                            barangEntity.getHargaBeli(),
                            barangEntity.getHargaJual(),
                            barangEntity.getTipeBarang(),
                            barangEntity.getKeterangan(),
                            rakitanMaster, marginPenjualan));
                }

            } else {
                entity.add(new BarangDetailPenjualanPerakitanDTO(
                        barangEntity.getKodeBarang(),
                        barangEntity.getNamaBarang(),
                        barangEntity.getJumlahBarang(),
                        barangEntity.getSatuanBarang(),
                        barangEntity.getHargaBeli(),
                        barangEntity.getHargaJual(),
                        barangEntity.getTipeBarang(),
                        barangEntity.getKeterangan(),
                        rakitanMaster, marginPenjualan));
            }

        }

        Resource fileRes = resLoad.getResource("classpath:report/barang-detail.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("kodeOutlet", getOutlet(idOutlet).get("kode"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] barcodeBarangReport(UUID idOutlet, String kodeBarang) throws FileNotFoundException, JRException, IOException {

        List<BarangEntity> entity = null;

        if (kodeBarang.equals("")) {
            entity = barangrepo.findBarang(idOutlet);
        } else {
            entity = barangrepo.findBarangByKode(idOutlet, kodeBarang.toLowerCase());
        }

        Resource fileRes = resLoad.getResource("classpath:report/barcode-barang.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
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
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] catatanReportByTipe(UUID idOutlet, int tipe, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<CatatanEntity> entity = catatanrepo.findCatatanByTipe(idOutlet, tipe, tanggalDari, tanggalHingga);
        Resource fileRes = null;
        if (tipe == 1) {
            fileRes = resLoad.getResource("classpath:report/catatan-pendapatan.jrxml");
        } else {
            fileRes = resLoad.getResource("classpath:report/catatan-pengeluaran.jrxml");
        }
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
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
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        String tipeKontakLabel = "";
        if (tipe == 0) {
            tipeKontakLabel = "Data Suplier";
        } else if (tipe == 1) {
            tipeKontakLabel = "Data Customer";
        } else if (tipe == 2) {
            tipeKontakLabel = "Data Personal";
        }
        map.put("tipeKontak", tipeKontakLabel);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] penjualanReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga, String idAkunKeuangan) throws FileNotFoundException, JRException, IOException {

        List<PenjualanReportDTO> entity = null;
        if (idAkunKeuangan.equals("")) {
            entity = penjualanrepo.findPenjualanMasterReport(idOutlet, tanggalDari, tanggalHingga);
        } else {
            UUID idAkun = UUID.fromString(idAkunKeuangan);
            entity = penjualanrepo.findPenjualanMasterReportByIdAkunKeuangan(idOutlet, idAkun, tanggalDari, tanggalHingga);
        }

        Resource fileRes = resLoad.getResource("classpath:report/penjualan.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }
    
        public byte[] penjualanV2Report(UUID idOutlet, Date tanggalDari, Date tanggalHingga, String idAkunKeuangan) throws FileNotFoundException, JRException, IOException {

        List<PenjualanReportV2DTO> entity = null;
        if (idAkunKeuangan.equals("")) {
            entity = penjualanrepo.findPenjualanV2Report(idOutlet, tanggalDari, tanggalHingga);
        } else {
            UUID idAkun = UUID.fromString(idAkunKeuangan);
            entity = penjualanrepo.findPenjualanV2ReportByIdAkunKeuangan(idOutlet, idAkun, tanggalDari, tanggalHingga);
        }

        Resource fileRes = resLoad.getResource("classpath:report/penjualan-v2.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] penjualanDetailReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga, String idAkunKeuangan) throws FileNotFoundException, JRException, IOException {
        List<PenjualanMasterEntity> entity = null;

        if (idAkunKeuangan.equals("")) {
            entity = penjualanrepo.findPenjualanReportDetail(idOutlet, tanggalDari, tanggalHingga);
        } else {
            UUID idAkun = UUID.fromString(idAkunKeuangan);
            entity = penjualanrepo.findPenjualanReportDetailByIdAkunKeuangan(idOutlet, idAkun, tanggalDari, tanggalHingga);
        }

        Resource fileRes = resLoad.getResource("classpath:report/penjualan-detail.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }
    
    
    public byte[] penjualanDetailDenganRefrensiPengeluaranReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga, String idAkunKeuangan) throws FileNotFoundException, JRException, IOException {
        List<PenjualanMasterEntity> entity = null;

        if (idAkunKeuangan.equals("")) {
            entity = penjualanrepo.findPenjualanReportDetail(idOutlet, tanggalDari, tanggalHingga);
        } else {
            UUID idAkun = UUID.fromString(idAkunKeuangan);
            entity = penjualanrepo.findPenjualanReportDetailByIdAkunKeuangan(idOutlet, idAkun, tanggalDari, tanggalHingga);
        }
        
        
                
        Resource fileRes = resLoad.getResource("classpath:report/penjualan-detail-dengan-refrensi-pengeluaran.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }
    

    public byte[] penjualanDetailPerkontakReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga, UUID idKartuKontak) throws FileNotFoundException, JRException, IOException {
        List<PenjualanMasterEntity> entity = penjualanrepo.findPenjualanReportDetailPerKontak(idOutlet, tanggalDari, tanggalHingga, idKartuKontak);
        Resource fileRes = resLoad.getResource("classpath:report/penjualan-detail-perkontak.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] pembelianReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga, String idAkunKeuangan) throws FileNotFoundException, JRException, IOException {
        List<PembelianReportDTO> entity = null;

        if (idAkunKeuangan.equals("")) {
            entity = pembelianrepo.findPembelianReport(idOutlet, tanggalDari, tanggalHingga);
        } else {
            UUID idAkun = UUID.fromString(idAkunKeuangan);
            entity = pembelianrepo.findPembelianReportByIdAkunKeuangan(idOutlet, idAkun, tanggalDari, tanggalHingga);
        }

        Resource fileRes = resLoad.getResource("classpath:report/pembelian.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }
    
    public byte[] pembelianV2Report(UUID idOutlet, Date tanggalDari, Date tanggalHingga, String idAkunKeuangan) throws FileNotFoundException, JRException, IOException {
        List<PembelianReportV2DTO> entity = null;

        if (idAkunKeuangan.equals("")) {
            entity = pembelianrepo.findPembelianV2Report(idOutlet, tanggalDari, tanggalHingga);
        } else {
            UUID idAkun = UUID.fromString(idAkunKeuangan);
            entity = pembelianrepo.findPembelianV2ReportByIdAkunKeuangan(idOutlet, idAkun, tanggalDari, tanggalHingga);
        }

        Resource fileRes = resLoad.getResource("classpath:report/pembelian-v2.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }
    
    

    public byte[] pembelianDetailReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga, String idAkunKeuangan) throws FileNotFoundException, JRException, IOException {
        List<PembelianMasterEntity> entity = null;

        if (idAkunKeuangan.equals("")) {
            entity = pembelianrepo.findPembelianReportDetail(idOutlet, tanggalDari, tanggalHingga);
        } else {
            UUID idAkun = UUID.fromString(idAkunKeuangan);
            entity = pembelianrepo.findPembelianReportDetailByIdAkunKeuangan(idOutlet, idAkun, tanggalDari, tanggalHingga);
        }

        Resource fileRes = resLoad.getResource("classpath:report/pembelian-detail.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] pembelianDetailPerkontakReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga, UUID idKartuKontak) throws FileNotFoundException, JRException, IOException {
        List<PembelianMasterEntity> entity = pembelianrepo.findPembelianReportDetailPerkontak(idOutlet, tanggalDari, tanggalHingga, idKartuKontak);
        Resource fileRes = resLoad.getResource("classpath:report/pembelian-detail-perkontak.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] penjualanMarginReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<MarginPenjualanDTO> entity = penjualanDetailRepo.findMarginPenjualan(idOutlet, tanggalDari, tanggalHingga);
        Resource fileRes = resLoad.getResource("classpath:report/penjualan-barang.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("kodeOutlet", getOutlet(idOutlet).get("kode"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
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
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
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
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] bukuBesar(UUID idOutlet, Date tanggalDari, Date tanggalHingga, UUID idAkun) throws FileNotFoundException, JRException, IOException {

        List<AkunKeuanganEntity> dataAkunKeuangan = null;

        if (idAkun == null) {
            dataAkunKeuangan = akunKeuanganRepo.getDataAkunKeuangan(idOutlet);
        } else {
            dataAkunKeuangan = akunKeuanganRepo.getDataAkunKeuanganById(idOutlet, idAkun);
        }

        List<BukuBesarDTO> lsbb = new ArrayList<>();
        for (int i = 0; i < dataAkunKeuangan.size(); i++) {
            BukuBesarDTO bb = new BukuBesarDTO();
            bb.setKodeAkun(dataAkunKeuangan.get(i).getKodeAkunKeuangan());
            bb.setNamaAkun(dataAkunKeuangan.get(i).getNamaAkunKeuangan());
            bb.setOpeningBalance(dataAkunKeuangan.get(i).getOpeningBalance());
            bb.setDetailJurnal(jurnalDetailRepo.getJurnalByAkunKeuangan(idOutlet, dataAkunKeuangan.get(i).getId(), tanggalDari, tanggalHingga));
            lsbb.add(bb);
        }

        Resource fileRes = resLoad.getResource("classpath:report/bukubesar.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lsbb);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] neracaSaldo(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<NeracaSaldoDTO> lns = new ArrayList<>();
        String sql = "SELECT a.kode_akun_keuangan,a.nama_akun_keuangan, "
                + "COALESCE((SELECT saldo FROM jurnal_umum_detail WHERE id_outlet=?1 "
                + "AND id_akun_keuangan=a.id AND "
                + "tanggal_jurnal >= ?2 AND tanggal_jurnal <= ?3 "
                + "ORDER BY urutan_global DESC LIMIT 1 ),0)"
                + "FROM akun_keuangan a WHERE a.id_outlet=?1 ORDER BY a.kode_akun_keuangan ASC";
        Query q = enma.createNativeQuery(sql);
        q.setParameter(1, idOutlet);
        q.setParameter(2, tanggalDari);
        q.setParameter(3, tanggalHingga);
        List<Object[]> lsData = q.getResultList();

        for (int i = 0; i < lsData.size(); i++) {
            NeracaSaldoDTO ns = new NeracaSaldoDTO();
            Object[] o = lsData.get(i);
            String kodeAkun = String.valueOf(o[0]);
            String namaAkun = String.valueOf(o[1]);
            double saldo = Double.parseDouble(String.valueOf(o[2]));
            double debit = 0;
            double kredit = 0;
            if (saldo >= 0) {
                debit = saldo;
                kredit = 0;
            } else if (saldo < 0) {
                debit = 0;
                kredit = saldo * -1;
            }
            ns.setKodeAkun(kodeAkun);
            ns.setNamaAkun(namaAkun);
            ns.setDebit(debit);
            ns.setKredit(kredit);
            lns.add(ns);
        }

        Resource fileRes = resLoad.getResource("classpath:report/neraca-saldo.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lns);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] neraca(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<NeracaSaldoDTO> lns = new ArrayList<>();
        String sql = "SELECT a.kode_akun_keuangan,a.nama_akun_keuangan, "
                + "COALESCE((SELECT saldo FROM jurnal_umum_detail WHERE id_outlet=?1 "
                + "AND id_akun_keuangan=a.id "
                + "AND tanggal_jurnal >= ?2 AND tanggal_jurnal <= ?3 "
                + "ORDER BY urutan_global DESC LIMIT 1 ),0)"
                + "FROM akun_keuangan a WHERE a.id_outlet=?1 AND (a.group_akun=1 OR a.group_akun=3) ORDER BY a.kode_akun_keuangan ASC";
        Query q = enma.createNativeQuery(sql);
        q.setParameter(1, idOutlet);
        q.setParameter(2, tanggalDari);
        q.setParameter(3, tanggalHingga);
        List<Object[]> lsData = q.getResultList();

        for (int i = 0; i < lsData.size(); i++) {
            NeracaSaldoDTO ns = new NeracaSaldoDTO();
            Object[] o = lsData.get(i);
            String kodeAkun = String.valueOf(o[0]);
            String namaAkun = String.valueOf(o[1]);
            double saldo = Double.parseDouble(String.valueOf(o[2]));
            double debit = 0;
            double kredit = 0;
            if (saldo >= 0) {
                debit = saldo;
                kredit = 0;
            } else if (saldo < 0) {
                debit = 0;
                kredit = saldo * -1;
            }
            ns.setKodeAkun(kodeAkun);
            ns.setNamaAkun(namaAkun);
            ns.setDebit(debit);
            ns.setKredit(kredit);
            lns.add(ns);
        }

        Resource fileRes = resLoad.getResource("classpath:report/neraca.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lns);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] labaRugi(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws FileNotFoundException, JRException, IOException {
        List<NeracaSaldoDTO> lns = new ArrayList<>();
        String sql = "SELECT a.kode_akun_keuangan,a.nama_akun_keuangan, "
                + "COALESCE((SELECT saldo FROM jurnal_umum_detail WHERE id_outlet=?1 "
                + "AND id_akun_keuangan=a.id "
                + "AND tanggal_jurnal >= ?2 AND tanggal_jurnal <= ?3 "
                + "ORDER BY urutan_global DESC LIMIT 1 ),0)"
                + "FROM akun_keuangan a WHERE a.id_outlet=?1 AND (a.group_akun=2 OR a.group_akun=4) ORDER BY a.kode_akun_keuangan ASC";
        Query q = enma.createNativeQuery(sql);
        q.setParameter(1, idOutlet);
        q.setParameter(2, tanggalDari);
        q.setParameter(3, tanggalHingga);
        List<Object[]> lsData = q.getResultList();

        for (int i = 0; i < lsData.size(); i++) {
            NeracaSaldoDTO ns = new NeracaSaldoDTO();
            Object[] o = lsData.get(i);
            String kodeAkun = String.valueOf(o[0]);
            String namaAkun = String.valueOf(o[1]);
            double saldo = Double.parseDouble(String.valueOf(o[2]));
            double debit = 0;
            double kredit = 0;
            if (saldo >= 0) {
                debit = saldo;
                kredit = 0;
            } else if (saldo < 0) {
                debit = 0;
                kredit = saldo * -1;
            }
            ns.setKodeAkun(kodeAkun);
            ns.setNamaAkun(namaAkun);
            ns.setDebit(debit);
            ns.setKredit(kredit);
            lns.add(ns);
        }

        Resource fileRes = resLoad.getResource("classpath:report/labarugi.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lns);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }

    public byte[] koreksiStok(UUID idOutlet, Date tanggalDari, Date tanggalHingga) throws IOException, JRException {
        List<KoreksiStokEntity> entity = koreksiStokRepo.findKoreksiStokReport(idOutlet, tanggalDari, tanggalHingga);
        Resource fileRes = resLoad.getResource("classpath:report/koreksi-stok.jrxml");
        InputStream inputStream = fileRes.getInputStream();
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
        map.put("alamatOutlet", getOutlet(idOutlet).get("alamat"));
        map.put("nohpOutlet", getOutlet(idOutlet).get("nohp"));
        map.put("namaOutlet", getOutlet(idOutlet).get("nama"));
        map.put("tanggalDari", tanggalDari);
        map.put("tanggalHingga", tanggalHingga);
        JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
        return JasperExportManager.exportReportToPdf(jp);
    }
}
