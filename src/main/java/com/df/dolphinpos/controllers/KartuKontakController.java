/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.KartuKontakEntity;
import com.df.dolphinpos.repositories.KartuKontakRepository;
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
@RequestMapping("/api/kartukontak")
public class KartuKontakController {

    @Autowired
    KartuKontakRepository kartukontakrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<KartuKontakEntity> getdata(Pageable pg, @PathVariable UUID idOutlet,
            @RequestParam String keyword) {
        Page<KartuKontakEntity> result = null;
        if (keyword.equals("")) {
            result = kartukontakrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = kartukontakrepo.findByIdOutletAndNamaKontakContainingIgnoreCase(pg, idOutlet, keyword);
        }

        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<KartuKontakEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return kartukontakrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @GetMapping("/getdatabytipe/{idOutlet}/{tipeKontak}")
    public Page<KartuKontakEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @PathVariable int tipeKontak) {
        Page<KartuKontakEntity> result = null;
        result = kartukontakrepo.findByIdOutletAndTipeKontak(pg, idOutlet, tipeKontak);

        return result;
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody KartuKontakEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            kartukontakrepo.save(data);
            KartuKontakEntity entity = kartukontakrepo.findById(data.getId()).orElseThrow(() -> new ResourceAccessException("Error"));
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaKontak() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody KartuKontakEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            KartuKontakEntity kartukontakentity = kartukontakrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            kartukontakentity.setNamaKontak(data.getNamaKontak());
            kartukontakentity.setAlamatKontak(data.getAlamatKontak());
            kartukontakentity.setEmailKontak(data.getEmailKontak());
            kartukontakentity.setNohpKontak(data.getNohpKontak());
            kartukontakentity.setDeskripsi(data.getDeskripsi());
            kartukontakentity.setTipeKontak(data.getTipeKontak());
            KartuKontakEntity entity = kartukontakrepo.save(kartukontakentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaKontak() + " berhasil diperbaharui");
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
            KartuKontakEntity kartukontakentity = kartukontakrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            kartukontakrepo.delete(kartukontakentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(kartukontakentity.getNamaKontak() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }
}
