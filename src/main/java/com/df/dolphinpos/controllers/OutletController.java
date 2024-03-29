/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.OutletEntity;
import com.df.dolphinpos.entities.OutletEntity;
import com.df.dolphinpos.repositories.OutletRepository;
import com.df.dolphinpos.repositories.OutletRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/outlet")
public class OutletController {

    @Autowired
    OutletRepository outletrepo;

    @GetMapping("/getdata")
    public Page<OutletEntity> getdata(Pageable pg) {
        return outletrepo.findAll(pg);
    }

    @GetMapping("/getdatabyid/{id}")
    public Optional<OutletEntity> getdatabyid(@PathVariable UUID id) {
        return outletrepo.findById(id);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody OutletEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            OutletEntity entity = outletrepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaOutlet() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody OutletEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            OutletEntity outletentity = outletrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            outletentity.setNamaOutlet(data.getNamaOutlet());
            outletentity.setAlamatOutlet(data.getAlamatOutlet());
            outletentity.setNohpOutlet(data.getNohpOutlet());
            outletentity.setTax(data.getTax());
            outletentity.setSettings(data.getSettings());
            OutletEntity entity = outletrepo.save(outletentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaOutlet() + " berhasil diperbaharui");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getLocalizedMessage());
        }
        return res;
    }

    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            OutletEntity outletentity = outletrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            outletrepo.delete(outletentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(outletentity.getNamaOutlet() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @GetMapping(value = "/getimage/{kodeOutlet}/{imagename}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFile(@PathVariable String kodeOutlet, @PathVariable String imagename) {
        Path path = null;
        byte[] databyte = null;
        try {
            path = Paths.get("images/" + kodeOutlet + "/" + imagename);
            databyte = Files.readAllBytes(path);
        } catch (Exception e) {
            path = Paths.get("images/digiposid.png");
            try {
                databyte = Files.readAllBytes(path);
            } catch (IOException ex) {
                Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return databyte;
    }

}
