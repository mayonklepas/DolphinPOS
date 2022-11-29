/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.MasterDetailPembelianDTO;
import com.df.dolphinpos.dto.MasterDetailRakitanDTO;
import com.df.dolphinpos.dto.PembelianMasterListDTO;
import com.df.dolphinpos.dto.RakitanMasterListDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.entities.RakitanDetailEntity;
import com.df.dolphinpos.entities.RakitanMasterEntity;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.PembelianDetailRepository;
import com.df.dolphinpos.repositories.PembelianMasterRepository;
import com.df.dolphinpos.repositories.RakitanDetailRepository;
import com.df.dolphinpos.repositories.RakitanMasterRepository;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/rakitan")
public class RakitanController {

    @Autowired
    RakitanMasterRepository rakitanmasterrepo;

    @Autowired
    RakitanDetailRepository rakitandetailrepo;

    @Autowired
    BarangRepository barangrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<RakitanMasterListDTO> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<RakitanMasterListDTO> result = null;
        if (keyword.equals("")) {
            result = rakitanmasterrepo.findAllRakitan(pg, idOutlet);
        } else {
            result = rakitanmasterrepo.findAllPembelianByKey(pg, idOutlet, keyword.toLowerCase());
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<RakitanMasterEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return rakitanmasterrepo.findByIdOutletAndId(idOutlet, id);
    }

    @GetMapping("/getdatadetailbyidrakitan/{idOutlet}/{idRakitanMaster}")
    public List<RakitanDetailEntity> getDataDetailByIdPenjualan(@PathVariable UUID idOutlet, @PathVariable UUID idRakitanMaster) {
        return rakitandetailrepo.findByIdRakitanMaster(idRakitanMaster);
    }

    @Transactional
    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody MasterDetailRakitanDTO data) {
        ResponseResult res = new ResponseResult();
        RakitanMasterEntity entityMaster = rakitanmasterrepo.save(data.getMaster());
        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdRakitanMaster(entityMaster.getId());
        }

        UUID uid = new UUID(0, 0);
        if (data.getMaster().getIdBarang() != null) {
            uid = data.getMaster().getIdBarang();
        }

        Optional<BarangEntity> barangMaster = barangrepo.findByIdOutletAndKodeBarang(data.getMaster().getIdOutlet(), data.getMaster().getKodeBarang());

        if (barangMaster.isPresent()) {
            BarangEntity barang = barangMaster.get();
            double jumlahbarangtambahi = barang.getJumlahBarang() + data.getMaster().getJumlahBarang();
            barang.setJumlahBarang(jumlahbarangtambahi);
            barangrepo.save(barang);
        } else {
            BarangEntity barang = new BarangEntity();
            barang.setKodeBarang(data.getMaster().getKodeBarang());
            barang.setNamaBarang(data.getMaster().getNamaBarang());
            barang.setSatuanBarang(data.getMaster().getSatuanBarang());
            barang.setJumlahBarang(data.getMaster().getJumlahBarang());
            barang.setHargaBeli(data.getMaster().getHargaModal());
            barang.setHargaJual(0);
            barang.setTipeBarang(1);
            barang.setKodeBarang(data.getMaster().getKodeBarang());
            barang.setIdOutlet(data.getMaster().getIdOutlet());
            barang.setIdPengguna(data.getMaster().getIdPengguna());
            barangrepo.save(barang);

            entityMaster.setIdBarang(barang.getId());

        }

        for (int i = 0; i < data.getDetail().size(); i++) {
            BarangEntity barangDetail = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
            double jumlahbarangkurangi = barangDetail.getJumlahBarang() - (data.getDetail().get(i).getJumlahIsi() * data.getMaster().getJumlahBarang());
            barangDetail.setJumlahBarang(jumlahbarangkurangi);
            barangrepo.save(barangDetail);

        }

        List<RakitanDetailEntity> entityDetail = rakitandetailrepo.saveAll(data.getDetail());

        MasterDetailRakitanDTO resraw = new MasterDetailRakitanDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetail);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getKodeBarang() + " No Perakitan " + entityMaster.getKodePerakitan() + " berhasil dirakit");
        res.setContent(resraw);

        return res;
    }

    @Transactional
    @PostMapping("/updatedata/{id}")
    public ResponseResult updatedatas(@RequestBody MasterDetailRakitanDTO data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();

        RakitanMasterEntity rakitanmasterentity = rakitanmasterrepo.findById(id).get();

        Optional<BarangEntity> barangMasterRaw = barangrepo.findByIdOutletAndKodeBarang(data.getMaster().getIdOutlet(), data.getMaster().getKodeBarang());

        if (barangMasterRaw.isPresent()) {
            BarangEntity barangMaster = barangMasterRaw.get();
            
            
            //pengembalian

            double jumlahbarangmasterkurangi = barangMaster.getJumlahBarang() - rakitanmasterentity.getJumlahBarang();
            barangMaster.setJumlahBarang(jumlahbarangmasterkurangi);
            barangrepo.save(barangMaster);

            List<RakitanDetailEntity> entityDetailSelect = rakitanmasterentity.getRakitanDetail();
            for (int i = 0; i < entityDetailSelect.size(); i++) {
                BarangEntity barang = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
                double jumlahbarangtambahi = barang.getJumlahBarang() + (entityDetailSelect.get(i).getJumlahIsi() * rakitanmasterentity.getJumlahBarang());
                barang.setJumlahBarang(jumlahbarangtambahi);
                barangrepo.save(barang);
            }

            
            //update data baru
            
            double jumlahbarangmastertambahi = barangMaster.getJumlahBarang() + data.getMaster().getJumlahBarang();
            barangMaster.setJumlahBarang(jumlahbarangmastertambahi);
            barangrepo.save(barangMaster);

            for (int i = 0; i < data.getDetail().size(); i++) {
                BarangEntity barang = barangrepo.findById(data.getDetail().get(i).getIdBarang()).get();
                double jumlahbarangkurangi = barang.getJumlahBarang() - (data.getDetail().get(i).getJumlahIsi() * data.getMaster().getJumlahBarang());
                barang.setJumlahBarang(jumlahbarangkurangi);
                barangrepo.save(barang);

            }
        } else {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage("Data barang tidak ditemukan");
            return res;
        }

        rakitanmasterentity.setTanggalPerakitan(data.getMaster().getTanggalPerakitan());
        rakitanmasterentity.setKodePerakitan(data.getMaster().getKodePerakitan());
        rakitanmasterentity.setNamaPerakitan(data.getMaster().getNamaPerakitan());
        rakitanmasterentity.setIdBarang(data.getMaster().getIdBarang());
        rakitanmasterentity.setKodeBarang(data.getMaster().getKodeBarang());
        rakitanmasterentity.setNamaBarang(data.getMaster().getNamaBarang());
        rakitanmasterentity.setSatuanBarang(data.getMaster().getSatuanBarang());
        rakitanmasterentity.setJumlahBarang(data.getMaster().getJumlahBarang());
        rakitanmasterentity.setHargaModal(data.getMaster().getHargaModal());
        RakitanMasterEntity entityMaster = rakitanmasterrepo.save(rakitanmasterentity);

        rakitandetailrepo.deleteByIdRakitanMaster(id);
        rakitandetailrepo.flush();

        for (int i = 0; i < data.getDetail().size(); i++) {
            data.getDetail().get(i).setIdRakitanMaster(id);
        }

        List<RakitanDetailEntity> entityDetailUpdate = rakitandetailrepo.saveAll(data.getDetail());

        MasterDetailRakitanDTO resraw = new MasterDetailRakitanDTO();
        resraw.setMaster(entityMaster);
        resraw.setDetail(entityDetailUpdate);

        res.setCode(0);
        res.setStatus("success");
        res.setMessage(entityMaster.getKodeBarang() + " No Perakitan " + entityMaster.getKodePerakitan() + " berhasil diperbaharui");
        res.setContent(resraw);
        return res;
    }

    @Transactional
    @DeleteMapping("/deletedata/{id}")
    public ResponseResult deletedata(@PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            RakitanMasterEntity rakitanmasterentity = rakitanmasterrepo.findById(id).get();
            
            BarangEntity barangMaster = barangrepo.findById(rakitanmasterentity.getIdBarang()).get();
            double jumlahbarangmasterkurangi = barangMaster.getJumlahBarang() - rakitanmasterentity.getJumlahBarang();
            barangMaster.setJumlahBarang(jumlahbarangmasterkurangi);
            barangrepo.save(barangMaster);

            List<RakitanDetailEntity> entityDetailSelect = rakitanmasterentity.getRakitanDetail();
            for (int i = 0; i < entityDetailSelect.size(); i++) {
                BarangEntity barangDetail = barangrepo.findById(entityDetailSelect.get(i).getIdBarang()).get();
                double jumlahbarangtambahi = barangDetail.getJumlahBarang() + (entityDetailSelect.get(i).getJumlahIsi() * rakitanmasterentity.getJumlahBarang());
                barangDetail.setJumlahBarang(jumlahbarangtambahi);
                barangrepo.save(barangDetail);
            }

            rakitanmasterrepo.delete(rakitanmasterentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(rakitanmasterentity.getKodePerakitan() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
