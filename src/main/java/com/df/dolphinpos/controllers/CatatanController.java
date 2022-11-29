/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.CatatanEntity;
import com.df.dolphinpos.repositories.CatatanRepository;
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
import com.df.dolphinpos.repositories.AkunKeuanganRepository;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/catatan")
public class CatatanController {

    @Autowired
    CatatanRepository catatanrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<CatatanEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<CatatanEntity> result = null;
        if (keyword.equals("")) {
            result = catatanrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = catatanrepo.findByIdOutletAndDeskripsiContainingIgnoreCase(pg, idOutlet, keyword);
        }

        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<CatatanEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return catatanrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody CatatanEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            CatatanEntity entity = catatanrepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getDeskripsi() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody CatatanEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            CatatanEntity catatanentity = catatanrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            catatanentity.setTanggalCatatan(data.getTanggalCatatan());
            catatanentity.setTipeCatatan(data.getTipeCatatan());
            catatanentity.setDeskripsi(data.getDeskripsi());
            catatanentity.setJumlah(data.getJumlah());
            catatanentity.setIdAkunKeuanganDebit(data.getIdAkunKeuanganDebit());
            catatanentity.setIdAkunKeuanganKredit(data.getIdAkunKeuanganKredit());
            CatatanEntity entity = catatanrepo.save(catatanentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getDeskripsi() + " berhasil diperbaharui");
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
            CatatanEntity catatanentity = catatanrepo.findById(id).get();
            catatanrepo.delete(catatanentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(catatanentity.getDeskripsi() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
