/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.MasterDetailPembelianDTO;
import com.df.dolphinpos.dto.MasterDetailReturPembelianDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.entities.ReturPembelianDetailEntity;
import com.df.dolphinpos.entities.ReturPembelianMasterEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.PembelianDetailRepository;
import com.df.dolphinpos.repositories.PembelianMasterRepository;
import com.df.dolphinpos.repositories.ReturPembelianDetailRepository;
import com.df.dolphinpos.repositories.ReturPembelianMasterRepository;
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
@RequestMapping("/api/retur-pembelian")
public class ReturPembelianController {

    @Autowired
    ReturPembelianMasterRepository returPembelianMasterrepo;

    @Autowired
    ReturPembelianDetailRepository returPembelianDetailrepo;

    @Autowired
    BarangRepository barangrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<ReturPembelianMasterEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<ReturPembelianMasterEntity> result = null;
        if (keyword.equals("")) {
            result = returPembelianMasterrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = returPembelianMasterrepo.findByIdOutletAndKodeReturPembelianMasterContainingIgnoreCase(pg, idOutlet, keyword);
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<ReturPembelianMasterEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return returPembelianMasterrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @Transactional
    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody MasterDetailReturPembelianDTO data) {
        ResponseResult res = new ResponseResult();
        ReturPembelianMasterEntity entityMaster = returPembelianMasterrepo.save(data.getMaster());
        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdReturPembelianMaster(entityMaster.getId());
        }

        for (int i = 0; i < data.getDetail().size(); i++) {

            UUID uid = new UUID(0, 0);
            if (data.getDetail().get(i).getIdBarang() != null) {
                uid = data.getDetail().get(i).getIdBarang();
            }
            boolean isExist = barangrepo.existsById(uid);
            if (isExist) {
                BarangEntity barang = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
                double jumlahbarangkurangi = barang.getJumlahBarang() - data.getDetail().get(i).getJumlahBeliRetur();
                barang.setJumlahBarang(jumlahbarangkurangi);
                barangrepo.save(barang);
            } else {
                BarangEntity barang = new BarangEntity();
                barang.setKodeBarang(data.getDetail().get(i).getKodeBarang());
                barang.setNamaBarang(data.getDetail().get(i).getNamaBarang());
                barang.setSatuanBarang(data.getDetail().get(i).getSatuanBarang());
                barang.setJumlahBarang(data.getDetail().get(i).getJumlahBeliRetur());
                barang.setHargaBeli(data.getDetail().get(i).getHargaBeliBeli());
                barang.setHargaJual(data.getDetail().get(i).getHargaJualBeli());
                barang.setTipeBarang(data.getDetail().get(i).getTipeBarang());
                barang.setKodeBarang(data.getDetail().get(i).getKodeBarang());
                barang.setIdOutlet(data.getDetail().get(i).getIdOutlet());
                barang.setIdPengguna(data.getDetail().get(i).getIdPengguna());
                BarangEntity newbarang = barangrepo.save(barang);
                data.getDetail().get(i).setIdBarang(newbarang.getId());
            }

        }

        List<ReturPembelianDetailEntity> entityDetail = returPembelianDetailrepo.saveAll(data.getDetail());

        MasterDetailReturPembelianDTO resraw = new MasterDetailReturPembelianDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetail);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getKodeReturPembelianMaster() + " berhasil ditambah");
        res.setContent(resraw);

        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedatas(@RequestBody MasterDetailReturPembelianDTO data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        ReturPembelianMasterEntity returpembelianmasterentity = returPembelianMasterrepo.findById(id).get();
        returpembelianmasterentity.setTanggalReturPembelian(data.getMaster().getTanggalReturPembelian());
        returpembelianmasterentity.setKodeReturPembelianMaster(data.getMaster().getKodeReturPembelianMaster());
        returpembelianmasterentity.setIdAkunKeuangan(data.getMaster().getIdAkunKeuangan());
        returpembelianmasterentity.setIdKartuKontak(data.getMaster().getIdKartuKontak());
        returpembelianmasterentity.setDeskripsi(data.getMaster().getDeskripsi());
        returpembelianmasterentity.setIdPengguna(data.getMaster().getIdPengguna());
        returpembelianmasterentity.setStatus(2);
        returpembelianmasterentity.setTax(data.getMaster().getTax());
        returpembelianmasterentity.setDisc(data.getMaster().getDisc());
        returpembelianmasterentity.setTotalBelanja(data.getMaster().getTotalBelanja());
        ReturPembelianMasterEntity entityMaster = returPembelianMasterrepo.save(returpembelianmasterentity);

        List<ReturPembelianDetailEntity> entityDetailSelect = returPembelianDetailrepo.findByIdReturPembelianMaster(id);
        for (int i = 0; i < entityDetailSelect.size(); i++) {
            BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
            double jumlahbarangtambahi = barang.getJumlahBarang() + entityDetailSelect.get(i).getJumlahBeliRetur();
            barang.setJumlahBarang(jumlahbarangtambahi);
            barangrepo.save(barang);
        }

        for (int i = 0; i < data.getDetail().size(); i++) {
            UUID uid = new UUID(0, 0);
            if (data.getDetail().get(i).getIdBarang() != null) {
                uid = data.getDetail().get(i).getIdBarang();
            }
            boolean isExist = barangrepo.existsById(uid);
            if (isExist) {
                BarangEntity barang = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
                double jumlahbarangkurangi = barang.getJumlahBarang() - data.getDetail().get(i).getJumlahBeliRetur();
                barang.setJumlahBarang(jumlahbarangkurangi);
                barangrepo.save(barang);
            } else {
                BarangEntity barang = new BarangEntity();
                barang.setKodeBarang(data.getDetail().get(i).getKodeBarang());
                barang.setNamaBarang(data.getDetail().get(i).getNamaBarang());
                barang.setSatuanBarang(data.getDetail().get(i).getSatuanBarang());
                barang.setJumlahBarang(data.getDetail().get(i).getJumlahBeliRetur());
                barang.setHargaBeli(data.getDetail().get(i).getHargaBeliBeli());
                barang.setHargaJual(data.getDetail().get(i).getHargaJualBeli());
                barang.setTipeBarang(data.getDetail().get(i).getTipeBarang());
                barang.setKodeBarang(data.getDetail().get(i).getKodeBarang());
                barang.setIdOutlet(data.getDetail().get(i).getIdOutlet());
                barang.setIdPengguna(data.getDetail().get(i).getIdPengguna());
                barangrepo.save(barang);
            }

        }

        returPembelianDetailrepo.deleteByIdReturPembelianMaster(id);
        returPembelianDetailrepo.flush();

        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdReturPembelianMaster(id);
        }

        List<ReturPembelianDetailEntity> entityDetailUpdate = returPembelianDetailrepo.saveAll(data.getDetail());

        MasterDetailReturPembelianDTO resraw = new MasterDetailReturPembelianDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetailUpdate);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getKodeReturPembelianMaster() + " berhasil diperbaharui");
        res.setContent(resraw);
        return res;
    }

    @Transactional
    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            List<ReturPembelianDetailEntity> entityDetailSelect = returPembelianDetailrepo.findByIdReturPembelianMaster(id);
            for (int i = 0; i < entityDetailSelect.size(); i++) {
                BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
                double jumlahbarangtambahi = barang.getJumlahBarang() + entityDetailSelect.get(i).getJumlahBeliRetur();
                barang.setJumlahBarang(jumlahbarangtambahi);
                barangrepo.save(barang);
            }
            ReturPembelianMasterEntity returPembelianMasterentity = returPembelianMasterrepo.findById(id).get();
            returPembelianMasterrepo.delete(returPembelianMasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(returPembelianMasterentity.getKodeReturPembelianMaster() + " berhasil dihapus");
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
            ReturPembelianMasterEntity returPembelianMasterentity = returPembelianMasterrepo.findById(id).get();
            returPembelianMasterentity.setStatus(status);
            if (status == 0) {
                returPembelianMasterentity.setStatusMessage("Pending");
            } else if (status == 1) {
                returPembelianMasterentity.setStatusMessage("Complete");
            } else if (status == 2) {
                returPembelianMasterentity.setStatusMessage("Edited to " + returPembelianMasterentity.getKodeReturPembelianMaster());
            }

            ReturPembelianMasterEntity entity = returPembelianMasterrepo.save(returPembelianMasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getKodeReturPembelianMaster() + " berhasil diperbaharui");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
