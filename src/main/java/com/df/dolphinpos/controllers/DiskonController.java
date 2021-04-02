/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.DiskonEntity;
import com.df.dolphinpos.repositories.DiskonRepository;
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
import org.springframework.web.client.ResourceAccessException;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/diskon")
public class DiskonController {

    @Autowired
    DiskonRepository diskonrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<DiskonEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<DiskonEntity> result = null;
        if (keyword.equals("")) {
            result = diskonrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = diskonrepo.findByIdOutletAndNamaDiskonContainingIgnoreCase(pg, idOutlet, keyword);
        }

        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<DiskonEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return diskonrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody DiskonEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            DiskonEntity entity = diskonrepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaDiskon() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody DiskonEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            DiskonEntity diskonentity = diskonrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            diskonentity.setNamaDiskon(data.getNamaDiskon());
            diskonentity.setMinimalPembelianSatu(data.getMinimalPembelianSatu());
            diskonentity.setMinimalPembelianDua(data.getMinimalPembelianDua());
            diskonentity.setNominalDiskonSatu(data.getNominalDiskonSatu());
            diskonentity.setNominalDiskonDua(data.getMinimalPembelianDua());
            diskonentity.setTipeDiskon(data.getTipeDiskon());
            diskonentity.setTanggalBerlakuHingga(data.getTanggalBerlakuHingga());
            DiskonEntity entity = diskonrepo.save(diskonentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaDiskon() + " berhasil diperbaharui");
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
            DiskonEntity diskonentity = diskonrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            diskonrepo.delete(diskonentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(diskonentity.getNamaDiskon() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
