/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.MasterDetailPenjualanDTO;
import com.df.dolphinpos.dto.MasterDetailReturPenjualanDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.dto.ReturPenjualanMasterListDTO;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.PenjualanDetailEntity;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
import com.df.dolphinpos.entities.RacikanEntity;
import com.df.dolphinpos.entities.ReturPenjualanDetailEntity;
import com.df.dolphinpos.entities.ReturPenjualanMasterEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.PenjualanDetailRepository;
import com.df.dolphinpos.repositories.PenjualanMasterRepository;
import com.df.dolphinpos.repositories.RacikanRepository;
import com.df.dolphinpos.repositories.ReturPenjualanDetailRepository;
import com.df.dolphinpos.repositories.ReturPenjualanMasterRepository;
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
@RequestMapping("/api/retur-penjualan")
public class ReturPenjualanController {

    @Autowired
    ReturPenjualanMasterRepository returPenjualanmasterrepo;

    @Autowired
    ReturPenjualanDetailRepository returPenjualandetailrepo;

    @Autowired
    BarangRepository barangrepo;

    @Autowired
    RacikanRepository racikarepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<ReturPenjualanMasterListDTO> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<ReturPenjualanMasterListDTO> result = null;
        if (keyword.equals("")) {
            result = returPenjualanmasterrepo.findAllReturPenjualan(pg, idOutlet);
        } else {
            result = returPenjualanmasterrepo.findAllReturPenjualanByKey(pg, idOutlet, keyword);
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<ReturPenjualanMasterEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return returPenjualanmasterrepo.findByIdOutletAndId(idOutlet, id);
    }

    @GetMapping("/getdatalist/{idOutlet}")
    public List<ReturPenjualanMasterEntity> getdatalist(@PathVariable UUID idOutlet) {
        return returPenjualanmasterrepo.findReturPenjualan(idOutlet);
    }

    @Transactional
    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody MasterDetailReturPenjualanDTO data) {
        ResponseResult res = new ResponseResult();
        ReturPenjualanMasterEntity entityMaster = returPenjualanmasterrepo.save(data.getMaster());
        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdReturPenjualanMaster(entityMaster.getId());
        }

        for (int i = 0; i < data.getDetail().size(); i++) {
            BarangEntity barang = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
            if (barang.getTipeBarang() != 2) {
                double jumlahbarangtambahi = barang.getJumlahBarang() + data.getDetail().get(i).getJumlahJualRetur();
                barang.setJumlahBarang(jumlahbarangtambahi);
                barangrepo.save(barang);
            }

        }

        List<ReturPenjualanDetailEntity> entityDetail = returPenjualandetailrepo.saveAll(data.getDetail());

        MasterDetailReturPenjualanDTO resraw = new MasterDetailReturPenjualanDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetail);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage("Transaksi berhasil ditambahkan");
        res.setContent(resraw);

        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedatas(@RequestBody MasterDetailReturPenjualanDTO data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        ReturPenjualanMasterEntity returPenjualanmasterentity = returPenjualanmasterrepo.findById(id).get();
        returPenjualanmasterentity.setTanggalReturPenjualan(data.getMaster().getTanggalReturPenjualan());
        returPenjualanmasterentity.setKodeReturPenjualanMaster(data.getMaster().getKodeReturPenjualanMaster());
        returPenjualanmasterentity.setIdAkunKeuangan(data.getMaster().getIdAkunKeuangan());
        returPenjualanmasterentity.setIdAkunKeuanganDebit(data.getMaster().getIdAkunKeuanganDebit());
        returPenjualanmasterentity.setIdKartuKontak(data.getMaster().getIdKartuKontak());
        returPenjualanmasterentity.setDeskripsi(data.getMaster().getDeskripsi());
        returPenjualanmasterentity.setIdPengguna(data.getMaster().getIdPengguna());
        returPenjualanmasterentity.setStatus(data.getMaster().getStatus());
        returPenjualanmasterentity.setTax(data.getMaster().getTax());
        returPenjualanmasterentity.setDisc(data.getMaster().getDisc());
        returPenjualanmasterentity.setTotalBelanja(data.getMaster().getTotalBelanja());
        ReturPenjualanMasterEntity entityMaster = returPenjualanmasterrepo.save(returPenjualanmasterentity);

        List<ReturPenjualanDetailEntity> entityDetailSelect = returPenjualandetailrepo.findByIdReturPenjualanMaster(id);

        for (int i = 0; i < entityDetailSelect.size(); i++) {
            BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
            if (barang.getTipeBarang() != 2) {
                double jumlahBarangKurangi = barang.getJumlahBarang() - entityDetailSelect.get(i).getJumlahJualRetur();
                barang.setJumlahBarang(jumlahBarangKurangi);
                barangrepo.save(barang);
            }
        }

        for (int i = 0; i < data.getDetail().size(); i++) {
            BarangEntity barang = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
            if (barang.getTipeBarang() != 2) {
                double jumlahbarangtambahi = barang.getJumlahBarang() + data.getDetail().get(i).getJumlahJualRetur();
                barang.setJumlahBarang(jumlahbarangtambahi);
                barangrepo.save(barang);
            }

        }

        returPenjualandetailrepo.deleteByIdReturPenjualanMaster(id);
        returPenjualandetailrepo.flush();

        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdReturPenjualanMaster(id);
        }

        List<ReturPenjualanDetailEntity> entityDetailUpdate = returPenjualandetailrepo.saveAll(data.getDetail());

        MasterDetailReturPenjualanDTO resraw = new MasterDetailReturPenjualanDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetailUpdate);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage("Transaksi berhasil diperbaharui");
        res.setContent(resraw);
        return res;
    }

    @Transactional
    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            List<ReturPenjualanDetailEntity> entityDetailSelect = returPenjualandetailrepo.findByIdReturPenjualanMaster(id);

            for (int i = 0; i < entityDetailSelect.size(); i++) {
                BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
                if (barang.getTipeBarang() != 2) {
                    double jumlahBarangTambahi = barang.getJumlahBarang() + entityDetailSelect.get(i).getJumlahJualRetur();
                    barang.setJumlahBarang(jumlahBarangTambahi);
                    barangrepo.save(barang);
                }
            }

            ReturPenjualanMasterEntity returPenjualanmasterentity = returPenjualanmasterrepo.findById(id).get();
            returPenjualanmasterrepo.delete(returPenjualanmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage("Transaksi berhasil dihapus");
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
            ReturPenjualanMasterEntity returPenjualanmasterentity = returPenjualanmasterrepo.findById(id).get();
            returPenjualanmasterentity.setStatus(status);
            ReturPenjualanMasterEntity entity = returPenjualanmasterrepo.save(returPenjualanmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage("Transaksi berhasil diperbaharui");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
