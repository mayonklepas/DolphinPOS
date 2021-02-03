/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.MasterDetailPenjualanDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.PenjualanDetailEntity;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
import com.df.dolphinpos.entities.RacikanEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.PenjualanDetailRepository;
import com.df.dolphinpos.repositories.PenjualanMasterRepository;
import com.df.dolphinpos.repositories.RacikanRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/penjualan")
public class PenjualanController {

    @Autowired
    PenjualanMasterRepository penjualanmasterrepo;

    @Autowired
    PenjualanDetailRepository penjualandetailrepo;

    @Autowired
    BarangRepository barangrepo;

    @Autowired
    RacikanRepository racikarepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<PenjualanMasterEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<PenjualanMasterEntity> result = null;
        if (keyword.equals("")) {
            result = penjualanmasterrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = penjualanmasterrepo.findByIdOutletAndKodePenjualanMasterContainingIgnoreCase(pg, idOutlet, keyword);
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<PenjualanMasterEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return penjualanmasterrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @GetMapping("/getdatalist")
    public List<PenjualanMasterEntity> getdatalist() {
        return penjualanmasterrepo.findPenjualan();
    }

    @Transactional
    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody MasterDetailPenjualanDTO data) {
        ResponseResult res = new ResponseResult();
        PenjualanMasterEntity entityMaster = penjualanmasterrepo.save(data.getMaster());
        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdPenjualanMaster(entityMaster.getId());
        }

        for (int i = 0; i < data.getDetail().size(); i++) {

            BarangEntity barang = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
            if (barang.getTipeBarang() == 2) {
                List<RacikanEntity> racikan = racikarepo.findByIdOutletAndKodeResep(entityMaster.getIdOutlet(), barang.getKodeResep());
                for (int j = 0; j < racikan.size(); j++) {
                    BarangEntity barangDalamRacikan = barangrepo.findById(racikan.get(j).getIdBarang()).get();
                    double jumlahResepKurangi = racikan.get(j).getJumlahIsi() * data.getDetail().get(i).getJumlahJual();
                    double jumlahBarangKurangi = barangDalamRacikan.getJumlahBarang() - jumlahResepKurangi;
                    barangDalamRacikan.setJumlahBarang(jumlahBarangKurangi);
                    barangrepo.save(barangDalamRacikan);
                }
            } else {
                double jumlahbarangkurangi = barang.getJumlahBarang() - data.getDetail().get(i).getJumlahJual();
                barang.setJumlahBarang(jumlahbarangkurangi);
                barangrepo.save(barang);
            }

        }

        List<PenjualanDetailEntity> entityDetail = penjualandetailrepo.saveAll(data.getDetail());

        MasterDetailPenjualanDTO resraw = new MasterDetailPenjualanDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetail);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getId() + " berhasil ditambahkan");
        res.setContent(resraw);

        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedatas(@RequestBody MasterDetailPenjualanDTO data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        PenjualanMasterEntity penjualanmasterentity = penjualanmasterrepo.findById(id).get();
        penjualanmasterentity.setTanggalPenjualan(data.getMaster().getTanggalPenjualan());
        penjualanmasterentity.setKodePenjualanMaster(data.getMaster().getKodePenjualanMaster());
        penjualanmasterentity.setIdAkunHolder(data.getMaster().getIdAkunHolder());
        penjualanmasterentity.setIdKartuKontak(data.getMaster().getIdKartuKontak());
        penjualanmasterentity.setDeskripsi(data.getMaster().getDeskripsi());
        penjualanmasterentity.setIdPengguna(data.getMaster().getIdPengguna());
        penjualanmasterentity.setStatus(data.getMaster().getStatus());
        penjualanmasterentity.setTax(data.getMaster().getTax());
        penjualanmasterentity.setDisc(data.getMaster().getDisc());
        PenjualanMasterEntity entityMaster = penjualanmasterrepo.save(penjualanmasterentity);

        List<PenjualanDetailEntity> entityDetailSelect = penjualandetailrepo.findByIdPenjualanMaster(id);

        for (int i = 0; i < entityDetailSelect.size(); i++) {
            BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
            if (barang.getTipeBarang() == 2) {
                List<RacikanEntity> racikan = racikarepo.findByIdOutletAndKodeResep(entityMaster.getIdOutlet(), barang.getKodeResep());
                for (int j = 0; j < racikan.size(); j++) {
                    BarangEntity barangDalamRacikan = barangrepo.findById(racikan.get(j).getIdBarang()).get();
                    double jumlahResepTambahi = racikan.get(j).getJumlahIsi() * entityDetailSelect.get(i).getJumlahJual();
                    double jumlahBarangTambahi = barangDalamRacikan.getJumlahBarang() + jumlahResepTambahi;
                    barangDalamRacikan.setJumlahBarang(jumlahBarangTambahi);
                    barangrepo.save(barangDalamRacikan);
                }
            } else {
                double jumlahBarangTambahi = barang.getJumlahBarang() + entityDetailSelect.get(i).getJumlahJual();
                barang.setJumlahBarang(jumlahBarangTambahi);
                barangrepo.save(barang);
            }
        }

        for (int i = 0; i < data.getDetail().size(); i++) {
            BarangEntity barang = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
            if (barang.getTipeBarang() == 2) {
                List<RacikanEntity> racikan = racikarepo.findByIdOutletAndKodeResep(entityMaster.getIdOutlet(), barang.getKodeResep());
                for (int j = 0; j < racikan.size(); j++) {
                    BarangEntity barangDalamRacikan = barangrepo.findById(racikan.get(j).getIdBarang()).get();
                    double jumlahResepKurangi = racikan.get(j).getJumlahIsi() * data.getDetail().get(i).getJumlahJual();
                    double jumlahBarangKurangi = barangDalamRacikan.getJumlahBarang() - jumlahResepKurangi;
                    barangDalamRacikan.setJumlahBarang(jumlahBarangKurangi);
                    barangrepo.save(barangDalamRacikan);
                }
            } else {
                double jumlahbarangkurangi = barang.getJumlahBarang() - data.getDetail().get(i).getJumlahJual();
                barang.setJumlahBarang(jumlahbarangkurangi);
                barangrepo.save(barang);
            }

        }

        penjualandetailrepo.deleteByIdPenjualanMaster(id);
        penjualandetailrepo.flush();

        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdPenjualanMaster(id);
        }

        List<PenjualanDetailEntity> entityDetailUpdate = penjualandetailrepo.saveAll(data.getDetail());

        MasterDetailPenjualanDTO resraw = new MasterDetailPenjualanDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetailUpdate);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getId() + " updated");
        res.setContent(resraw);
        return res;
    }

    @Transactional
    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            List<PenjualanDetailEntity> entityDetailSelect = penjualandetailrepo.findByIdPenjualanMaster(id);

            for (int i = 0; i < entityDetailSelect.size(); i++) {
                BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
                if (barang.getTipeBarang() == 2) {
                    List<RacikanEntity> racikan = racikarepo.findByIdOutletAndKodeResep(entityDetailSelect.get(i).getIdOutlet(), barang.getKodeResep());
                    for (int j = 0; j < racikan.size(); j++) {
                        BarangEntity barangDalamRacikan = barangrepo.findById(racikan.get(j).getIdBarang()).get();
                        double jumlahResepTambahi = racikan.get(j).getJumlahIsi() * entityDetailSelect.get(i).getJumlahJual();
                        double jumlahBarangTambahi = barangDalamRacikan.getJumlahBarang() + jumlahResepTambahi;
                        barangDalamRacikan.setJumlahBarang(jumlahBarangTambahi);
                        barangrepo.save(barangDalamRacikan);
                    }
                } else {
                    double jumlahBarangTambahi = barang.getJumlahBarang() + entityDetailSelect.get(i).getJumlahJual();
                    barang.setJumlahBarang(jumlahBarangTambahi);
                    barangrepo.save(barang);
                }
            }

            PenjualanMasterEntity penjualanmasterentity = penjualanmasterrepo.findById(id).get();
            penjualanmasterrepo.delete(penjualanmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(penjualanmasterentity.getId() + " deleted");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Transactional
    @PutMapping("/updatestatus/{id}/{status}")
    public ResponseResult updatedata(@PathVariable UUID id, @PathVariable int status) {
        ResponseResult res = new ResponseResult();
        try {
            PenjualanMasterEntity penjualanmasterentity = penjualanmasterrepo.findById(id).get();
            penjualanmasterentity.setStatus(status);
            PenjualanMasterEntity entity = penjualanmasterrepo.save(penjualanmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getId() + " added");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
