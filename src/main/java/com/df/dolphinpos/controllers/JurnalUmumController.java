/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.MasterDetailJurnalUmumDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.JurnalUmumDetailEntity;
import com.df.dolphinpos.entities.JurnalUmumMasterEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.JurnalUmumDetailRepository;
import com.df.dolphinpos.repositories.JurnalUmumMasterRepository;
import com.df.dolphinpos.service.AccountingService;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/jurnalumum")
public class JurnalUmumController {

    @Autowired
    JurnalUmumMasterRepository jurnalumummasterrepo;

    @Autowired
    JurnalUmumDetailRepository jurnalumumdetailrepo;

    @Autowired
    AccountingService accServ;

    @GetMapping("/getdata/{idOutlet}")
    public Page<JurnalUmumMasterEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<JurnalUmumMasterEntity> result = null;
        if (keyword.equals("")) {
            result = jurnalumummasterrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = jurnalumummasterrepo.findByIdOutletAndNoRefContainingIgnoreCase(pg, idOutlet, keyword);
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<JurnalUmumMasterEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return jurnalumummasterrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @Transactional
    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody MasterDetailJurnalUmumDTO data) {
        ResponseResult res = new ResponseResult();
        JurnalUmumMasterEntity entityMaster = jurnalumummasterrepo.save(data.getMaster());
        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdJurnalMaster(entityMaster.getId());
        }

        List<JurnalUmumDetailEntity> entityDetail = jurnalumumdetailrepo.saveAll(data.getDetail());

        MasterDetailJurnalUmumDTO resraw = new MasterDetailJurnalUmumDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetail);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getNoRef() + " berhasil ditambah");
        res.setContent(resraw);

        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedatas(@RequestBody MasterDetailJurnalUmumDTO data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        JurnalUmumMasterEntity jurnalumummasterentity = jurnalumummasterrepo.findById(id).get();
        jurnalumummasterentity.setTanggalJurnal(data.getMaster().getTanggalJurnal());
        jurnalumummasterentity.setNoRef(data.getMaster().getNoRef());
        jurnalumummasterentity.setTanggalRef(data.getMaster().getTanggalJurnal());
        jurnalumummasterentity.setIdPengguna(data.getMaster().getIdPengguna());
        JurnalUmumMasterEntity entityMaster = jurnalumummasterrepo.save(jurnalumummasterentity);

        jurnalumumdetailrepo.deleteByIdJurnalMaster(id);
        jurnalumumdetailrepo.flush();

        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdJurnalMaster(id);
        }

        List<JurnalUmumDetailEntity> entityDetailUpdate = jurnalumumdetailrepo.saveAll(data.getDetail());

        MasterDetailJurnalUmumDTO resraw = new MasterDetailJurnalUmumDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetailUpdate);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getNoRef() + " berhasil diperbaharui");
        res.setContent(resraw);
        return res;
    }

    @Transactional
    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            JurnalUmumMasterEntity jurnalumummasterentity = jurnalumummasterrepo.findById(id).get();
            jurnalumummasterrepo.delete(jurnalumummasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(jurnalumummasterentity.getNoRef() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @GetMapping("/posting/{idOutlet}/{idPengguna}")
    public ResponseResult postingData(@PathVariable UUID idOutlet,@PathVariable UUID idPengguna) {
        ResponseResult res = new ResponseResult();
        try {
            accServ.postingData(idOutlet,idPengguna);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage("Berhasil di posting");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }
    
     @GetMapping("/reindextahundata/{idOutlet}")
    public ResponseResult tutupBukuTahunan(@PathVariable UUID idOutlet) {
        ResponseResult res = new ResponseResult();
        try {
            accServ.Reindextahundata(idOutlet, Calendar.getInstance().get(Calendar.YEAR));
            res.setCode(0);
            res.setStatus("success");
            res.setMessage("Berhasil di proses");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
