/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.RacikanMasterDto;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.RacikanEntity;
import com.df.dolphinpos.repositories.RacikanRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/api/racikan")
public class RacikanController {

    @Autowired
    RacikanRepository racikanrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<RacikanMasterDto> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<RacikanMasterDto> result = null;
        if (keyword.equals("")) {
            result = racikanrepo.findByIdOutletGroupByKodeResep(pg, idOutlet);
        } else {
            result = racikanrepo.findByIdOutletGroupByKodeResepAndKey(pg, idOutlet, keyword.toLowerCase());
        }

        return result;
    }


    @GetMapping("/getdatabykoderesep/{idOutlet}/{kodeResep}")
    public List<RacikanEntity> getdatabykode(@PathVariable UUID idOutlet, @PathVariable String kodeResep) {
        List<RacikanEntity> detailRacikan=racikanrepo.findByIdOutletAndKodeResep(idOutlet, kodeResep);
        List<RacikanEntity> detailRacikan2=detailRacikan;
        return detailRacikan2;
    }

    @PostMapping("/adddata/{idOutlet}")
    public ResponseResult adddata(@RequestBody List<RacikanEntity> data, @PathVariable UUID idOutlet) {
        ResponseResult res = new ResponseResult();
        try {
            int deletestatus = racikanrepo.deleteBykodeResep(idOutlet, data.get(0).getKodeResep());
            Iterable<RacikanEntity> entity = racikanrepo.saveAll(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage("Resep " + entity.iterator().next().getNamaResep() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

  
    @DeleteMapping("/deletedata/{idOutlet}/{kodeResep}")
    public ResponseResult deletedata(@PathVariable UUID idOutlet, @PathVariable String kodeResep) {
        ResponseResult res = new ResponseResult();
        try {
            int deletestatus = racikanrepo.deleteBykodeResep(idOutlet, kodeResep);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage("Resep berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
