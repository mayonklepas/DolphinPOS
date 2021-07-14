/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.MasterDetailHutangPiutangDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.PembayaranHutangPiutangEntity;
import com.df.dolphinpos.entities.HutangPiutangEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.PembayaranHutangPiutangRepository;
import com.df.dolphinpos.repositories.HutangPiutangRepository;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/hutangpiutang")
public class HutangPiutangController {

    @Autowired
    HutangPiutangRepository hutangpiutangmasterrepo;

    @Autowired
    PembayaranHutangPiutangRepository hutangpiutangdetailrepo;

    @GetMapping("/getdata/{idOutlet}/{tipe}")
    public Page<HutangPiutangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet,@PathVariable int tipe, @RequestParam String keyword) {
        Page<HutangPiutangEntity> result = null;
        if (keyword.equals("")) {
            result = hutangpiutangmasterrepo.findByIdOutletAndTipe(pg, idOutlet, tipe);
        } else {
            result = hutangpiutangmasterrepo.findBySearch(pg, idOutlet,tipe,keyword);
        }
        return result;
    }

    @GetMapping("/pembayaran/getdata/{idHutangPiutang{/{idOutlet}")
    public Page<PembayaranHutangPiutangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, UUID idHutangPiutang, @RequestParam String keyword) {
        Page<PembayaranHutangPiutangEntity> result = null;
        if (keyword.equals("")) {
            result = hutangpiutangdetailrepo.findByIdHutangPiutangAndIdOutlet(pg, idOutlet, idHutangPiutang);
        } else {
            result = hutangpiutangdetailrepo.findByIdHutangPiutangAndDeskripsiContainingIgnoreCase(pg, idHutangPiutang, keyword);
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<HutangPiutangEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return hutangpiutangmasterrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @Transactional
    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody HutangPiutangEntity data) {
        ResponseResult res = new ResponseResult();
        HutangPiutangEntity entity = hutangpiutangmasterrepo.save(data);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getKartuKontak().getNamaKontak() + " " + entity.getDeskripsi() + " berhasil ditambah");
        res.setContent(entity);
        return res;
    }

    @Transactional
    @PostMapping("pembayaran/adddata/{idHutangPiutang}")
    public ResponseResult adddata(@RequestBody PembayaranHutangPiutangEntity data, @PathVariable UUID idHutangPiutang) {
        ResponseResult res = new ResponseResult();
        PembayaranHutangPiutangEntity entity = hutangpiutangdetailrepo.save(data);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getDeskripsi() + " berhasil ditambah");
        res.setContent(entity);
        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedatas(@RequestBody HutangPiutangEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        HutangPiutangEntity entity = hutangpiutangmasterrepo.findById(id).get();
        entity.setTanggal(data.getTanggal());
        entity.setTipe(data.getTipe());
        entity.setDeskripsi(data.getDeskripsi());
        entity.setJumlah(data.getJumlah());
        entity.setIdKartuKontak(data.getIdKartuKontak());
        entity.setIdAkunKeuangan(data.getIdAkunKeuangan());
        entity.setIdPengguna(data.getIdPengguna());
        HutangPiutangEntity entitySave = hutangpiutangmasterrepo.save(entity);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getKartuKontak().getNamaKontak() + " berhasil diperbaharui");
        res.setContent(entitySave);
        return res;
    }

    @Transactional
    @PostMapping("pembayaran/updatedata/{idHutangPiutang}/{id}")
    public ResponseResult updatedatas(@RequestBody PembayaranHutangPiutangEntity data, @PathVariable UUID idHutangPiutang, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        PembayaranHutangPiutangEntity entity = hutangpiutangdetailrepo.findById(id).get();
        entity.setTanggal(data.getTanggal());
        entity.setIdHutangPiutang(idHutangPiutang);
        entity.setDeskripsi(data.getDeskripsi());
        entity.setJumlah(data.getJumlah());
        PembayaranHutangPiutangEntity entitySave = hutangpiutangdetailrepo.save(entity);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getDeskripsi() + " berhasil diperbaharui");
        res.setContent(entitySave);
        return res;
    }

    @Transactional
    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            HutangPiutangEntity hutangpiutangmasterentity = hutangpiutangmasterrepo.findById(id).get();
            hutangpiutangmasterrepo.delete(hutangpiutangmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(hutangpiutangmasterentity.getKartuKontak().getNamaKontak() + " " + hutangpiutangmasterentity.getDeskripsi() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Transactional
    @DeleteMapping("pembayaran/deletedata/{idHutangPiutang}/{id}")
    public ResponseResult deletedata(@PathVariable UUID id, @PathVariable UUID idHutangPiutang) {
        ResponseResult res = new ResponseResult();
        try {
            PembayaranHutangPiutangEntity entity = hutangpiutangdetailrepo.findById(id).get();
            hutangpiutangdetailrepo.delete(entity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getDeskripsi() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
