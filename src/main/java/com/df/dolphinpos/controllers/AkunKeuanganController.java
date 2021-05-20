/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.AkunKeuanganEntity;
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
@RequestMapping("/api/akunkeuangan")
public class AkunKeuanganController {

    @Autowired
    AkunKeuanganRepository akunkeuanganrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<AkunKeuanganEntity> getdata(Pageable pg, @PathVariable UUID idOutlet,@RequestParam String keyword) {
        Page<AkunKeuanganEntity> result = null;
        if (keyword.equals("")) {
            result = akunkeuanganrepo.findByIdOutlet(pg, idOutlet);
        }else{
            result = akunkeuanganrepo.findByIdOutletAndNamaAkunKeuanganContainingIgnoreCase(pg,idOutlet,keyword);
        }

        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<AkunKeuanganEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return akunkeuanganrepo.findByIdAndIdOutlet(idOutlet, id);
    }
    
    
    @GetMapping("/getdatabykode/{kodeAkunKeuangan}/{idOutlet}")
    public Optional<AkunKeuanganEntity> getdatabykode(@PathVariable String kodeAkunKeuangan, @PathVariable UUID idOutlet) {
        return akunkeuanganrepo.findByKodeAkunKeuanganAndIdOutlet(kodeAkunKeuangan, idOutlet);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody AkunKeuanganEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            AkunKeuanganEntity entity = akunkeuanganrepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaAkunKeuangan()+" berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody AkunKeuanganEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            AkunKeuanganEntity akunkeuanganentity = akunkeuanganrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            akunkeuanganentity.setKodeAkunKeuangan(data.getKodeAkunKeuangan());
            akunkeuanganentity.setNamaAkunKeuangan(data.getNamaAkunKeuangan());
            akunkeuanganentity.setOpeningBalance(data.getOpeningBalance());
            akunkeuanganentity.setCurrentBalance(data.getCurrentBalance());
            akunkeuanganentity.setTipeAkun(data.getTipeAkun());
            akunkeuanganentity.setGroupAkun(data.getGroupAkun());
            akunkeuanganentity.setDeskripsiAkunKeuangan(data.getDeskripsiAkunKeuangan());
            AkunKeuanganEntity entity = akunkeuanganrepo.save(akunkeuanganentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaAkunKeuangan()+" berhasil diperbaharui");
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
            AkunKeuanganEntity akunkeuanganentity = akunkeuanganrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            akunkeuanganrepo.delete(akunkeuanganentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(akunkeuanganentity.getNamaAkunKeuangan() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }
}
