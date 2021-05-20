/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.KoreksiStokEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.KoreksiStokRepository;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/koreksistok")
public class KoreksiStokController {

    @Autowired
    KoreksiStokRepository koreksistokrepo;

    @Autowired
    BarangRepository barangRepository;

    @GetMapping("/getdata/{idOutlet}")
    public Page<KoreksiStokEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<KoreksiStokEntity> result = null;
        if (keyword.equals("")) {
            result = koreksistokrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = koreksistokrepo.findByIdOutletAndDeskripsiContainingIgnoreCase(pg, idOutlet, keyword);
        }

        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<KoreksiStokEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return koreksistokrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @Transactional
    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody KoreksiStokEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            KoreksiStokEntity entity = koreksistokrepo.save(data);
            if (data.getTipeKoreksi() == 0) {
                BarangEntity barangEntity = barangRepository.findById(data.getIdBarang()).get();
                double tambahbarang = barangEntity.getJumlahBarang() + data.getJumlahKoreksi();
                barangEntity.setJumlahBarang(tambahbarang);
                barangRepository.save(barangEntity);
            } else {
                BarangEntity barangEntity = barangRepository.findById(data.getIdBarang()).get();
                double kurangbarang = barangEntity.getJumlahBarang() - data.getJumlahKoreksi();
                barangEntity.setJumlahBarang(kurangbarang);
                barangRepository.save(barangEntity);
            }
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

    @Transactional
    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody KoreksiStokEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try { 

            KoreksiStokEntity koreksistokentity = koreksistokrepo.findById(id).orElseThrow(() -> new ResourceAccessException("Error"));
            double jumlahKoreksi = koreksistokentity.getJumlahKoreksi();
            koreksistokentity.setTanggalKoreksi(data.getTanggalKoreksi());
            koreksistokentity.setIdBarang(data.getIdBarang());
            koreksistokentity.setTipeKoreksi(data.getTipeKoreksi());
            koreksistokentity.setJumlahKoreksi(data.getJumlahKoreksi());
            koreksistokentity.setDeskripsi(data.getDeskripsi());
            KoreksiStokEntity entity = koreksistokrepo.save(koreksistokentity);
            if (data.getTipeKoreksi() == 0) {
                BarangEntity barangEntity = barangRepository.findById(data.getIdBarang()).get();
                double tambahbarang = (barangEntity.getJumlahBarang() - jumlahKoreksi) + data.getJumlahKoreksi();
                barangEntity.setJumlahBarang(tambahbarang);
                barangRepository.save(barangEntity);
            } else {
                BarangEntity barangEntity = barangRepository.findById(data.getIdBarang()).get();
                double kurangbarang = (barangEntity.getJumlahBarang() + jumlahKoreksi) - data.getJumlahKoreksi();
                barangEntity.setJumlahBarang(kurangbarang);
                barangRepository.save(barangEntity);
            }
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
            KoreksiStokEntity koreksistokentity = koreksistokrepo.findById(id).get();
            if (koreksistokentity.getTipeKoreksi() == 0) {
                BarangEntity barangEntity = barangRepository.findById(koreksistokentity.getIdBarang()).get();
                double kurangiBarang = (barangEntity.getJumlahBarang() - koreksistokentity.getJumlahKoreksi());
                barangEntity.setJumlahBarang(kurangiBarang);
                barangRepository.save(barangEntity);
            } else {
                BarangEntity barangEntity = barangRepository.findById(koreksistokentity.getIdBarang()).get();
                double tambahiBarang = (barangEntity.getJumlahBarang() + koreksistokentity.getJumlahKoreksi());
                barangEntity.setJumlahBarang(tambahiBarang);
                barangRepository.save(barangEntity);
            }
            koreksistokrepo.delete(koreksistokentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(koreksistokentity.getDeskripsi() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
