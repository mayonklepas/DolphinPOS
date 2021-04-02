/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import java.util.Optional;
import java.util.UUID;
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
@RequestMapping("/api/barang")
public class BarangController {

    @Autowired
    BarangRepository barangrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<BarangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<BarangEntity> result = null;
        if (keyword.equals("")) {
            result = barangrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = barangrepo.findByIdOutletAndNamaBarangContainingIgnoreCase(pg, idOutlet, keyword);
        }

        return result;
    }

    @GetMapping("/getdatabykey/{idOutlet}")
    public Page<BarangEntity> getdatabykey(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<BarangEntity> result = null;
        if (keyword.equals("")) {
            result = barangrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = barangrepo.findByKey(pg, idOutlet, keyword.toLowerCase());
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<BarangEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return barangrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @GetMapping("/getdatabykode/{idOutlet}/{kodeBarang}")
    public Optional<BarangEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable String kodeBarang) {
        return barangrepo.findByIdOutletAndKodeBarang(idOutlet, kodeBarang);
    }

    @GetMapping("/getdatabytipe/{idOutlet}/{tipe}")
    public Page<BarangEntity> getdatabytipe(Pageable pg, @PathVariable UUID idOutlet, @PathVariable int tipe) {
        return barangrepo.findByIdOutletAndTipeBarang(pg, idOutlet, tipe);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody BarangEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            BarangEntity entity = barangrepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaBarang() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody BarangEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            BarangEntity barangentity = barangrepo.findById(id).get();
            barangentity.setKodeBarang(data.getKodeBarang());
            barangentity.setNamaBarang(data.getNamaBarang());
            barangentity.setSatuanBarang(data.getSatuanBarang());
            barangentity.setJumlahBarang(data.getJumlahBarang());
            barangentity.setHargaBeli(data.getHargaBeli());
            barangentity.setHargaJual(data.getHargaJual());
            barangentity.setTipeBarang(data.getTipeBarang());
            barangentity.setKodeDiskon(data.getKodeDiskon());
            barangentity.setKodeResep(data.getKodeResep());
            BarangEntity entity = barangrepo.save(barangentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaBarang() + " berhasil diperbaharui");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            BarangEntity barangentity = barangrepo.findById(id).get();
            barangrepo.delete(barangentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(barangentity.getNamaBarang() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(String.valueOf(e));
        }
        return res;
    }

}
