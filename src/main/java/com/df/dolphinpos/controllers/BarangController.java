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
import com.df.dolphinpos.entities.OutletEntity;
import com.df.dolphinpos.repositories.BarangDummyRepository;
import com.df.dolphinpos.repositories.BarangRepository;
import com.df.dolphinpos.repositories.OutletRepository;
import com.df.dolphinpos.service.UtilService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    @Autowired
    UtilService utilServe;

    @Autowired
    OutletRepository outletRepo;

    @GetMapping("/getdata/{idOutlet}")
    public Page<BarangEntity> getdata(Pageable pg, @PathVariable UUID idOutlet, @RequestParam String keyword, @RequestParam String tipe) {
        Page<BarangEntity> result = null;
        if (keyword.equals("")) {
            if (tipe.equals("")) {
                result = barangrepo.findByIdOutlet(pg, idOutlet);
            } else {
                result = barangrepo.findByIdOutletAndTipeBarang(pg, idOutlet, Integer.parseInt(tipe));
            }
        } else {
            if (tipe.equals("")) {
                result = barangrepo.findByAll(pg, idOutlet, keyword);
            } else {
                result = barangrepo.findByAll(pg, idOutlet, Integer.parseInt(tipe), keyword);
            }
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
            Optional<BarangEntity> barangDetail = barangrepo.findByIdOutletAndKodeBarang(data.getIdOutlet(), data.getKodeBarang());
            if (barangDetail.isPresent()) {
                res.setCode(1);
                res.setStatus("failed");
                res.setMessage("Barang dengan kode ini sudah ada");
                return res;
            }
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

            Optional<OutletEntity> outlet = outletRepo.findById(idOutlet);

            if (!outlet.isPresent()) {
                res.setCode(1);
                res.setStatus("Failed");
                res.setMessage("Outlet tidak ditemukan");
            }

            byte[] bytedata = file.getBytes();
            File dest = new File("images/" + outlet.get().getKodeOutlet());
            if (!dest.exists()) {
                dest.mkdir();
            }
            Path path = Paths.get("images/" + outlet.get().getKodeOutlet() + "/" + nama + ".jpg");
            Files.write(path, utilServe.resizeImage(bytedata), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            res.setCode(0);
            res.setStatus("Success");
            res.setMessage("Gambar berhasil diupload");

        } catch (IOException ex) {
            res.setCode(1);
            res.setStatus("Failed");
            res.setMessage("Gagal diupload, Terjadi kesalahan");
            Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @GetMapping(value = "/getimage/{kodeOutlet}/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getFile(@PathVariable String kodeOutlet, @PathVariable String imagename) {
        Path path = null;
        byte[] databyte = null;
        try {
            path = Paths.get("images/" + kodeOutlet + "/" + imagename);
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
    public BarangTotalDTO getTotal(@PathVariable UUID idOutlet, @RequestParam String tipe) {
        if (tipe.equals("")) {
            BarangTotalDTO data = barangrepo.getTotal(idOutlet);
            return data;
        } else {
            BarangTotalDTO data = barangrepo.getTotalBytipe(idOutlet, Integer.parseInt(tipe));
            return data;
        }

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
