/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.MasterDetailHutangDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.PembayaranHutangEntity;
import com.df.dolphinpos.entities.HutangEntity;
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
import com.df.dolphinpos.repositories.HutangRepository;
import com.df.dolphinpos.repositories.PembayaranHutangRepository;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/hutang")
public class HutangController {

    @Autowired
    HutangRepository hutangmasterrepo;

    @Autowired
    PembayaranHutangRepository hutangdetailrepo;

    @Autowired
    UtilService utilServ;

    @GetMapping("/getdata/{idOutlet}")
    public Page<HutangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<HutangEntity> result = null;
        if (keyword.equals("")) {
            result = hutangmasterrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = hutangmasterrepo.findBySearch(pg, idOutlet, keyword);
        }
        return result;
    }

    @GetMapping("/pembayaran/getdata/{idOutlet}/{idHutang}")
    public Page<PembayaranHutangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet,@PathVariable UUID idHutang, @RequestParam String keyword) {
        Page<PembayaranHutangEntity> result = null;
        if (keyword.equals("")) {
            result = hutangdetailrepo.findByIdOutletAndIdHutang(pg, idOutlet, idHutang);
        } else {
            result = hutangdetailrepo.findByIdOutletAndIdHutangAndDeskripsiContainingIgnoreCase(pg, idOutlet, idHutang, keyword);
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<HutangEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return hutangmasterrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @Transactional
    @PostMapping("/adddata/{idOutlet}")
    public ResponseResult adddata(@RequestBody HutangEntity data,@PathVariable UUID idOutlet) {
        ResponseResult res = new ResponseResult();
        String invKode = utilServ.getNoInvoice(idOutlet, "hutang");
        data.setKode(invKode);
        HutangEntity entity = hutangmasterrepo.save(data);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getDeskripsi() + " berhasil ditambah");
        res.setContent(entity);
        return res;
    }

    @Transactional
    @PostMapping("pembayaran/adddata/{idOutlet}")
    public ResponseResult pembayaranadddata(@RequestBody PembayaranHutangEntity data, @PathVariable UUID idOutlet) {
        ResponseResult res = new ResponseResult();
        String kodePembayaran = utilServ.getNoInvoice(idOutlet, "pembayaran_hutang");
        data.setKodePembayaran(kodePembayaran);
        PembayaranHutangEntity entity = hutangdetailrepo.save(data);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getDeskripsi() + " berhasil ditambah");
        res.setContent(entity);
        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody HutangEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        HutangEntity entity = hutangmasterrepo.findById(id).get();
        entity.setTanggal(data.getTanggal());
        entity.setDeskripsi(data.getDeskripsi());
        entity.setJumlah(data.getJumlah());
        entity.setIdKartuKontak(data.getIdKartuKontak());
        entity.setIdAkunKeuangan(data.getIdAkunKeuangan());
        entity.setIdAkunKeuanganKredit(data.getIdAkunKeuanganKredit());
        entity.setIdPengguna(data.getIdPengguna());
        HutangEntity entitySave = hutangmasterrepo.save(entity);
        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entity.getDeskripsi() + " berhasil diperbaharui");
        res.setContent(entitySave);
        return res;
    }

    @Transactional
    @PostMapping("pembayaran/updatedata/{id}")
    public ResponseResult pembayaranupdatedata(@RequestBody PembayaranHutangEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        PembayaranHutangEntity entity = hutangdetailrepo.findById(id).get();
        entity.setTanggal(data.getTanggal());
        entity.setIdHutang(data.getIdHutang());
        entity.setAkunKeuangan(data.getAkunKeuangan());
        entity.setAkunKeuanganDebit(data.getAkunKeuanganDebit());
        entity.setDeskripsi(data.getDeskripsi());
        entity.setJumlah(data.getJumlah());
        PembayaranHutangEntity entitySave = hutangdetailrepo.save(entity);
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
            HutangEntity hutangpiutangmasterentity = hutangmasterrepo.findById(id).get();
            hutangmasterrepo.delete(hutangpiutangmasterentity);
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
    @DeleteMapping("pembayaran/deletedata/{idHutang}/{id}")
    public ResponseResult pembayarandeletedata(@PathVariable UUID id, @PathVariable UUID idHutangPiutang) {
        ResponseResult res = new ResponseResult();
        try {
            PembayaranHutangEntity entity = hutangdetailrepo.findById(id).get();
            hutangdetailrepo.delete(entity);
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
