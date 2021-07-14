/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.AkunKeuanganDummyEntity;
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
import com.df.dolphinpos.repositories.AkunKeuanganDummyRepository;
import java.util.List;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/akunkeuangandummy")
public class AkunKeuanganDummyController {

    @Autowired
    AkunKeuanganDummyRepository akunkeuangandummyrepo;

    @GetMapping("/getdata")
    public List<AkunKeuanganDummyEntity> getdata() {
        return akunkeuangandummyrepo.findAll();
    }


    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody AkunKeuanganDummyEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            AkunKeuanganDummyEntity entity = akunkeuangandummyrepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaAkunKeuangan() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody AkunKeuanganDummyEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            AkunKeuanganDummyEntity akunkeuangandummyentity = akunkeuangandummyrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            akunkeuangandummyentity.setKodeAkunKeuangan(data.getKodeAkunKeuangan());
            akunkeuangandummyentity.setNamaAkunKeuangan(data.getNamaAkunKeuangan());
            akunkeuangandummyentity.setTipeAkun(data.getTipeAkun());
            akunkeuangandummyentity.setGroupAkun(data.getGroupAkun());
            akunkeuangandummyentity.setDeskripsiAkunKeuangan(data.getDeskripsiAkunKeuangan());
            AkunKeuanganDummyEntity entity = akunkeuangandummyrepo.save(akunkeuangandummyentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaAkunKeuangan() + " berhasil diperbaharui");
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
            AkunKeuanganDummyEntity akunkeuangandummyentity = akunkeuangandummyrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            akunkeuangandummyrepo.delete(akunkeuangandummyentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(akunkeuangandummyentity.getNamaAkunKeuangan() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }
    
}
