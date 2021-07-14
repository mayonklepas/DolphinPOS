/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.entities.BarangDummyEntity;
import com.df.dolphinpos.repositories.BarangDummyRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/barangdummy")
public class BarangDummyController {

    @Autowired
    BarangDummyRepository barangdummyrepo;
   

    @GetMapping("/getdata")
    public List<BarangDummyEntity> getdata() {      
        return barangdummyrepo.findAll();
    }

    @GetMapping("/getdatabytipetoko/{tipetoko}")
    public List<BarangDummyEntity> getdatabytipe(@PathVariable int tipetoko) {
        return barangdummyrepo.findByTipeToko(tipetoko);
    }

    @PostMapping("/adddata")
    public ResponseResult adddata(@RequestBody BarangDummyEntity data) {
        ResponseResult res = new ResponseResult();
        try {
            BarangDummyEntity entity = barangdummyrepo.save(data);
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
    public ResponseResult updatedata(@RequestBody BarangDummyEntity data, @PathVariable UUID id) {
        ResponseResult res = new ResponseResult();
        try {
            BarangDummyEntity barangdummyentity = barangdummyrepo.findById(id).get();
            barangdummyentity.setKodeBarang(data.getKodeBarang());
            barangdummyentity.setNamaBarang(data.getNamaBarang());
            barangdummyentity.setSatuanBarang(data.getSatuanBarang());
            barangdummyentity.setHargaBeli(data.getHargaBeli());
            barangdummyentity.setHargaJual(data.getHargaJual());
            barangdummyentity.setTipeBarang(data.getTipeBarang());
            barangdummyentity.setTipeToko(data.getTipeToko());
            barangdummyentity.setKeterangan(data.getKeterangan());
            BarangDummyEntity entity = barangdummyrepo.save(barangdummyentity);
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
            BarangDummyEntity barangdummyentity = barangdummyrepo.findById(id).get();
            barangdummyrepo.delete(barangdummyentity);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage(barangdummyentity.getNamaBarang() + " berhasil dihapus");
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
                            + "dengan kode barangdummy yang berbeda");
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
            Logger.getLogger(BarangDummyController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(BarangDummyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return databyte;
    }
    

}
