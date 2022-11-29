/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.PembayaranPiutangEntity;
import com.df.dolphinpos.entities.PiutangEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.service.AccountingService;
import com.df.dolphinpos.service.UtilService;
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
import com.df.dolphinpos.repositories.PiutangRepository;
import com.df.dolphinpos.repositories.PembayaranPiutangRepository;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/piutang")
public class PiutangController {

    @Autowired
    PiutangRepository piutangmasterrepo;

    @Autowired
    PembayaranPiutangRepository piutangdetailrepo;

    @Autowired
    UtilService utilServ;

    @GetMapping("/getdata/{idOutlet}")
    public Page<PiutangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<PiutangEntity> result = null;
        if (keyword.equals("")) {
            result = piutangmasterrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = piutangmasterrepo.findBySearch(pg, idOutlet, keyword);
        }
        return result;
    }

    @GetMapping("/pembayaran/getdata/{idOutlet}/{idPiutang}")
    public Page<PembayaranPiutangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet,@PathVariable UUID idPiutang, @RequestParam String keyword) {
        Page<PembayaranPiutangEntity> result = null;
        if (keyword.equals("")) {
            result = piutangdetailrepo.findByIdOutletAndIdPiutang(pg, idOutlet, idPiutang);
        } else {
            result = piutangdetailrepo.findByIdOutletAndIdPiutangAndDeskripsiContainingIgnoreCase(pg, idOutlet, idPiutang, keyword);
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<PiutangEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return piutangmasterrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @Transactional
    @PostMapping("/adddata/{idOutlet}")
    public ResponseResult adddata(@RequestBody PiutangEntity data,@PathVariable UUID idOutlet) {
        ResponseResult res = new ResponseResult();
        String invKode = utilServ.getNoInvoice(idOutlet, "piutang");
        data.setKode(invKode);
        PiutangEntity entity = piutangmasterrepo.save(data);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getDeskripsi() + " berhasil ditambah");
        res.setContent(entity);
        return res;
    }

    @Transactional
    @PostMapping("pembayaran/adddata/{idOutlet}")
    public ResponseResult pembayaranadddata(@RequestBody PembayaranPiutangEntity data, @PathVariable UUID idOutlet) {
        ResponseResult res = new ResponseResult();
        String kodePembayaran = utilServ.getNoInvoice(idOutlet, "pembayaran_piutang");
        data.setKodePembayaran(kodePembayaran);
        PembayaranPiutangEntity entity = piutangdetailrepo.save(data);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getDeskripsi() + " berhasil ditambah");
        res.setContent(entity);
        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody PiutangEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        PiutangEntity entity = piutangmasterrepo.findById(id).get();
        entity.setTanggal(data.getTanggal());
        entity.setDeskripsi(data.getDeskripsi());
        entity.setJumlah(data.getJumlah());
        entity.setIdKartuKontak(data.getIdKartuKontak());
        entity.setIdAkunKeuangan(data.getIdAkunKeuangan());
        entity.setIdAkunKeuanganDebit(data.getIdAkunKeuanganDebit());
        entity.setIdPengguna(data.getIdPengguna());
        PiutangEntity entitySave = piutangmasterrepo.save(entity);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getDeskripsi() + " berhasil diperbaharui");
        res.setContent(entitySave);
        return res;
    }

    @Transactional
    @PostMapping("pembayaran/updatedata/{id}")
    public ResponseResult pembayaranupdatedata(@RequestBody PembayaranPiutangEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        PembayaranPiutangEntity entity = piutangdetailrepo.findById(id).get();
        entity.setTanggal(data.getTanggal());
        entity.setIdPiutang(data.getIdPiutang());
        entity.setAkunKeuangan(data.getAkunKeuangan());
        entity.setAkunKeuanganKredit(data.getAkunKeuanganKredit());
        entity.setDeskripsi(data.getDeskripsi());
        entity.setJumlah(data.getJumlah());
        PembayaranPiutangEntity entitySave = piutangdetailrepo.save(entity);
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
            PiutangEntity piutangpiutangmasterentity = piutangmasterrepo.findById(id).get();
            piutangmasterrepo.delete(piutangpiutangmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(piutangpiutangmasterentity.getKartuKontak().getNamaKontak() + " " + piutangpiutangmasterentity.getDeskripsi() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Transactional
    @DeleteMapping("pembayaran/deletedata/{idPiutang}/{id}")
    public ResponseResult pembayarandeletedata(@PathVariable UUID id, @PathVariable UUID idPiutangPiutang) {
        ResponseResult res = new ResponseResult();
        try {
            PembayaranPiutangEntity entity = piutangdetailrepo.findById(id).get();
            piutangdetailrepo.delete(entity);
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
