/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.LoginFormDto;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.PenggunaEntity;
import com.df.dolphinpos.repositories.PenggunaRepository;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/pengguna")
public class PenggunaController {

    @Autowired
    PenggunaRepository penggunarepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<PenggunaEntity> getdata(Pageable pg, @PathVariable UUID idOutlet) {
        return penggunarepo.findByIdOutlet(pg, idOutlet);
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<PenggunaEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return penggunarepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody PenggunaEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            PenggunaEntity entity = penggunarepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaPengguna() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody PenggunaEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            PenggunaEntity penggunaentity = penggunarepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            penggunaentity.setNamaPengguna(data.getNamaPengguna());
            penggunaentity.setAlamatPengguna(data.getAlamatPengguna());
            penggunaentity.setEmailPengguna(data.getEmailPengguna());
            penggunaentity.setNohpPengguna(data.getNohpPengguna());
            penggunaentity.setUsername(data.getUsername());
            penggunaentity.setPassword(data.getPassword());
            penggunaentity.setRules(data.getRules());
            PenggunaEntity entity = penggunarepo.save(penggunaentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaPengguna() + " berhasil diperbaharui");
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
            PenggunaEntity penggunaentity = penggunarepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            penggunarepo.delete(penggunaentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(penggunaentity.getNamaPengguna() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginFormDto data) {
        ResponseResult res = new ResponseResult();
        try {
            PenggunaEntity entity = penggunarepo.findLogin(data.getKodeOutlet(), data.getUsername(), data.getPassword());
            if (entity != null) {
                res.setCode(0);
                res.setStatus("success");
                res.setMessage("berhasil login");
                res.setContent(entity);
            } else {
                res.setCode(1);
                res.setStatus("failed");
                res.setMessage("gagal login");
            }
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
