/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.AkunHolderEntity;
import com.df.dolphinpos.repositories.AkunHolderRepository;
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
@RequestMapping("/api/akunholder")
public class AkunHolderController {

    @Autowired
    AkunHolderRepository akunholderrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<AkunHolderEntity> getdata(Pageable pg, @PathVariable UUID idOutlet,@RequestParam String keyword) {
        Page<AkunHolderEntity> result = null;
        if (keyword.equals("")) {
            result = akunholderrepo.findByIdOutlet(pg, idOutlet);
        }else{
            result = akunholderrepo.findByIdOutletAndNamaAkunHolderContainingIgnoreCase(pg,idOutlet,keyword);
        }

        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<AkunHolderEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return akunholderrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody AkunHolderEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            AkunHolderEntity entity = akunholderrepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getId() + " added");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody AkunHolderEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            AkunHolderEntity akunholderentity = akunholderrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            akunholderentity.setNamaAkunHolder(data.getNamaAkunHolder());
            akunholderentity.setDeskripsiAkunHolder(data.getDeskripsiAkunHolder());
            AkunHolderEntity entity = akunholderrepo.save(akunholderentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getId() + " updated");
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
            AkunHolderEntity akunholderentity = akunholderrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            akunholderrepo.delete(akunholderentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(akunholderentity.getNamaAkunHolder() + " deleted");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }
}
