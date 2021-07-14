/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.BarangTotalDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangDummyEntity;
import com.df.dolphinpos.entities.BarangEntity;
import com.df.dolphinpos.repositories.BarangDummyRepository;
import com.df.dolphinpos.repositories.BarangRepository;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/barang")
public class BarangController {

    @Autowired
    BarangRepository barangrepo;

    @Autowired
    BarangDummyRepository barangdummyrepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<BarangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<BarangEntity> result = null;
        if (keyword.equals("")) {
            result = barangrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = barangrepo.findByIdOutletAndNamaBarangContainingIgnoreCase(pg, idOutlet, keyword);
        }

        return result;
    }

    @GetMapping("/getdatabykey/{idOutlet}")
    public Page<BarangEntity> getdatabykey(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword) {
        Page<BarangEntity> result = null;
        if (keyword.equals("")) {
            result = barangrepo.findByIdOutlet(pg, idOutlet);
        } else {
            result = barangrepo.findByKey(pg, idOutlet, keyword.toLowerCase());
        }
        return result;
    }

    @GetMapping("/getdatabyid/{idOutlet}/{id}")
    public Optional<BarangEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable UUID id) {
        return barangrepo.findByIdAndIdOutlet(idOutlet, id);
    }

    @GetMapping("/getdatabykode/{idOutlet}/{kodeBarang}")
    public Optional<BarangEntity> getdatabyid(@PathVariable UUID idOutlet, @PathVariable String kodeBarang) {
        return barangrepo.findByIdOutletAndKodeBarang(idOutlet, kodeBarang);
    }

    @GetMapping("/getdatabytipe/{idOutlet}/{tipe}")
    public Page<BarangEntity> getdatabytipe(Pageable pg, @PathVariable UUID idOutlet, @PathVariable int tipe) {
        return barangrepo.findByIdOutletAndTipeBarang(pg, idOutlet, tipe);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody BarangEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            BarangEntity entity = barangrepo.save(data);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaBarang() + " berhasil ditambahkan");
            res.setContent(entity);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PutMapping("/updatedata/{id}")
    public ResponseResult updatedata(@RequestBody BarangEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            BarangEntity barangentity = barangrepo.findById(id).get();
            barangentity.setKodeBarang(data.getKodeBarang());
            barangentity.setNamaBarang(data.getNamaBarang());
            barangentity.setSatuanBarang(data.getSatuanBarang());
            barangentity.setJumlahBarang(data.getJumlahBarang());
            barangentity.setHargaBeli(data.getHargaBeli());
            barangentity.setHargaJual(data.getHargaJual());
            barangentity.setTipeBarang(data.getTipeBarang());
            barangentity.setKodeDiskon(data.getKodeDiskon());
            barangentity.setKodeResep(data.getKodeResep());
            barangentity.setKeterangan(data.getKeterangan());
            BarangEntity entity = barangrepo.save(barangentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(entity.getNamaBarang() + " berhasil diperbaharui");
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
            BarangEntity barangentity = barangrepo.findById(id).get();
            barangrepo.delete(barangentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(barangentity.getNamaBarang() + " berhasil dihapus");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(String.valueOf(e));
        }
        return res;
    }

    @PostMapping("/uploadimage/{idOutlet}")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile file,
            @PathVariable UUID idOutlet,
            @RequestParam("nama") String nama) {
        ResponseResult res = new ResponseResult();
        try {
            String fileName = file.getOriginalFilename();
            String[] fileNameSplit = fileName.split("\\.");
            int fileNameSplitSize = fileNameSplit.length;
            String ext = fileNameSplit[fileNameSplitSize - 1];

            if (ext.equals("jpg")) {
                byte[] bytedata = file.getBytes();
                Path path = Paths.get("images/" + nama + "." + ext);
                boolean isAda = Files.exists(path);
                if (isAda == true) {
                    res.setCode(0);
                    res.setStatus("Failed");
                    res.setMessage("Gambar dengan nama ini sudah ada, "
                            + "silahkan pakai yang sudah ada atau upload gambar "
                            + "dengan kode barang yang berbeda");
                } else {
                    Files.write(path, bytedata);
                    res.setCode(0);
                    res.setStatus("Success");
                    res.setMessage("Gambar berhasil diupload");
                }
            } else {
                res.setCode(1);
                res.setStatus("Failed");
                res.setMessage("Gambar tidak sesuai format, format harus jpg");
            }

        } catch (IOException ex) {
            res.setCode(1);
            res.setStatus("Failed");
            res.setMessage("Gagal diupload, Terjadi kesalahan");
            Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @GetMapping(value = "/getimage/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getFile(@PathVariable String imagename) {
        Path path = null;
        byte[] databyte = null;
        try {
            path = Paths.get("images/" + imagename);
            databyte = Files.readAllBytes(path);
        } catch (Exception e) {
            path = Paths.get("images/defimg.jpg");
            try {
                databyte = Files.readAllBytes(path);
            } catch (IOException ex) {
                Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return databyte;
    }

    @GetMapping(value = "/getbarangtotal/{idOutlet}")
    public BarangTotalDTO getTotal(@PathVariable UUID idOutlet) {
        BarangTotalDTO data = barangrepo.getTotal(idOutlet);
        return data;
    }

    @GetMapping("/generatedefault/{idOutlet}/{idPengguna}/{tipeToko)")
    public ResponseResult generatedefault(@PathVariable UUID idOutlet, @PathVariable UUID idPengguna, @PathVariable int tipeToko) {
        ResponseResult res = new ResponseResult();
        try {
            List<BarangEntity> listBarang = barangrepo.findBarang(idOutlet);
            if (!listBarang.isEmpty()) {
                throw new Exception("Generate hanya bisa dilakukan saat data kosong");
            }
            List<BarangEntity> listDataSave = new ArrayList<BarangEntity>();
            List<BarangDummyEntity> barangDummy = barangdummyrepo.findByTipeToko(tipeToko);
            for (BarangDummyEntity dataDummy : barangDummy) {
                BarangEntity dataSave = new BarangEntity();
                dataSave.setKodeBarang(dataDummy.getKodeBarang());
                dataSave.setNamaBarang(dataDummy.getNamaBarang());
                dataSave.setSatuanBarang(dataDummy.getSatuanBarang());
                dataSave.setJumlahBarang(0);
                dataSave.setHargaBeli(dataDummy.getHargaBeli());
                dataSave.setHargaJual(dataDummy.getHargaJual());
                dataSave.setTipeBarang(dataDummy.getTipeBarang());
                dataSave.setKeterangan(dataDummy.getKeterangan());
                listDataSave.add(dataSave);
            }
            barangrepo.saveAll(listDataSave);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(" barang berhasil digenerate");
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
