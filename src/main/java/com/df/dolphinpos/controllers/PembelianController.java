/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.MasterDetailPembelianDTO;
import com.df.dolphinpos.dto.PembelianMasterListDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.PembelianDetailRepository;
import com.df.dolphinpos.repositories.PembelianMasterRepository;
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
@RequestMapping("/api/pembelian")
public class PembelianController {

    @Autowired
    PembelianMasterRepository pembelianmasterrepo;

    @Autowired
    PembelianDetailRepository pembeliandetailrepo;

    @Autowired
    BarangRepository barangrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<PembelianMasterListDTO> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<PembelianMasterListDTO> result = null;
        if (keyword.equals("")) {
            result = pembelianmasterrepo.findAllPembelian(pg, idOutlet);
        } else {
            result = pembelianmasterrepo.findAllPembelianByKey(pg, idOutlet, keyword.toLowerCase());
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<PembelianMasterEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return pembelianmasterrepo.findByIdOutletAndId(idOutlet, id);
    }

    @GetMapping("/getdatadetailbyidpembelian/{idOutlet}/{idPembelian}")
    public List<PembelianDetailEntity> getDataDetailByIdPenjualan(@PathVariable UUID idOutlet, @PathVariable UUID idPembelian) {
        return pembeliandetailrepo.findByIdPembelianMaster(idPembelian);
    }

    @Transactional
    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody MasterDetailPembelianDTO data) {
        ResponseResult res = new ResponseResult();
        PembelianMasterEntity entityMaster = pembelianmasterrepo.save(data.getMaster());
        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdPembelianMaster(entityMaster.getId());
        }

        for (int i = 0; i < data.getDetail().size(); i++) {

            UUID uid = new UUID(0, 0);
            if (data.getDetail().get(i).getIdBarang() != null) {
                uid = data.getDetail().get(i).getIdBarang();
            }
            boolean isExist = barangrepo.existsById(uid);
            if (isExist) {
                BarangEntity barang = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
                double jumlahbarangkurangi = barang.getJumlahBarang() + data.getDetail().get(i).getJumlahBeli();
                barang.setJumlahBarang(jumlahbarangkurangi);
                barangrepo.save(barang);
            } else {
                BarangEntity barang = new BarangEntity();
                barang.setKodeBarang(data.getDetail().get(i).getKodeBarang());
                barang.setNamaBarang(data.getDetail().get(i).getNamaBarang());
                barang.setSatuanBarang(data.getDetail().get(i).getSatuanBarang());
                barang.setJumlahBarang(data.getDetail().get(i).getJumlahBeli());
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

        List<PembelianDetailEntity> entityDetail = pembeliandetailrepo.saveAll(data.getDetail());

        MasterDetailPembelianDTO resraw = new MasterDetailPembelianDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetail);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getKodePembelianMaster() + " berhasil ditambah");
        res.setContent(resraw);

        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedatas(@RequestBody MasterDetailPembelianDTO data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        PembelianMasterEntity pembelianmasterentity = pembelianmasterrepo.findById(id).get();
        pembelianmasterentity.setTanggalPembelian(data.getMaster().getTanggalPembelian());
        pembelianmasterentity.setKodePembelianMaster(data.getMaster().getKodePembelianMaster());
        pembelianmasterentity.setIdAkunKeuangan(data.getMaster().getIdAkunKeuangan());
        pembelianmasterentity.setIdAkunKeuanganDebit(data.getMaster().getIdAkunKeuanganDebit());
        pembelianmasterentity.setIdKartuKontak(data.getMaster().getIdKartuKontak());
        pembelianmasterentity.setDeskripsi(data.getMaster().getDeskripsi());
        pembelianmasterentity.setIdPengguna(data.getMaster().getIdPengguna());
        pembelianmasterentity.setStatus(2);
        pembelianmasterentity.setTax(data.getMaster().getTax());
        pembelianmasterentity.setDisc(data.getMaster().getDisc());
        pembelianmasterentity.setTotalBelanja(data.getMaster().getTotalBelanja());
        PembelianMasterEntity entityMaster = pembelianmasterrepo.save(pembelianmasterentity);

        List<PembelianDetailEntity> entityDetailSelect = pembeliandetailrepo.findByIdPembelianMaster(id);
        for (int i = 0; i < entityDetailSelect.size(); i++) {
            BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
            double jumlahbarangkurangi = barang.getJumlahBarang() - entityDetailSelect.get(i).getJumlahBeli();
            barang.setJumlahBarang(jumlahbarangkurangi);
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
                double jumlahbarangkurangi = barang.getJumlahBarang() + data.getDetail().get(i).getJumlahBeli();
                barang.setJumlahBarang(jumlahbarangkurangi);
                barangrepo.save(barang);
            } else {
                BarangEntity barang = new BarangEntity();
                barang.setKodeBarang(data.getDetail().get(i).getKodeBarang());
                barang.setNamaBarang(data.getDetail().get(i).getNamaBarang());
                barang.setSatuanBarang(data.getDetail().get(i).getSatuanBarang());
                barang.setJumlahBarang(data.getDetail().get(i).getJumlahBeli());
                barang.setHargaBeli(data.getDetail().get(i).getHargaBeliBeli());
                barang.setHargaJual(data.getDetail().get(i).getHargaJualBeli());
                barang.setTipeBarang(data.getDetail().get(i).getTipeBarang());
                barang.setKodeBarang(data.getDetail().get(i).getKodeBarang());
                barang.setIdOutlet(data.getDetail().get(i).getIdOutlet());
                barang.setIdPengguna(data.getDetail().get(i).getIdPengguna());
                barangrepo.save(barang);
            }

        }

        pembeliandetailrepo.deleteByIdPembelianMaster(id);
        pembeliandetailrepo.flush();

        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdPembelianMaster(id);
        }

        List<PembelianDetailEntity> entityDetailUpdate = pembeliandetailrepo.saveAll(data.getDetail());

        MasterDetailPembelianDTO resraw = new MasterDetailPembelianDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetailUpdate);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getKodePembelianMaster() + " berhasil diperbaharui");
        res.setContent(resraw);
        return res;
    }

    @Transactional
    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            List<PembelianDetailEntity> entityDetailSelect = pembeliandetailrepo.findByIdPembelianMaster(id);
            for (int i = 0; i < entityDetailSelect.size(); i++) {
                BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
                double jumlahbarangkurangi = barang.getJumlahBarang() - entityDetailSelect.get(i).getJumlahBeli();
                barang.setJumlahBarang(jumlahbarangkurangi);
                barangrepo.save(barang);
            }
            PembelianMasterEntity pembelianmasterentity = pembelianmasterrepo.findById(id).get();
            pembelianmasterrepo.delete(pembelianmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(pembelianmasterentity.getKodePembelianMaster() + " berhasil dihapus");
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
            PembelianMasterEntity pembelianmasterentity = pembelianmasterrepo.findById(id).get();
            pembelianmasterentity.setStatus(status);
            if (status == 0) {
                pembelianmasterentity.setStatusMessage("Pending");
            } else if (status == 1) {
                pembelianmasterentity.setStatusMessage("Complete");
            } else if (status == 2) {
                pembelianmasterentity.setStatusMessage("Edited to " + pembelianmasterentity.getKodePembelianMaster());
            }

            PembelianMasterEntity entity = pembelianmasterrepo.save(pembelianmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getKodePembelianMaster() + " berhasil diperbaharui");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
