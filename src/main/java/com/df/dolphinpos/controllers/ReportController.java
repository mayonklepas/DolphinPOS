/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.service.ReportService;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportservice;

    @GetMapping("/barang")
    public ResponseEntity<InputStreamResource> getlaporanstok() throws FileNotFoundException, JRException {
        byte[] bytes = reportservice.barangReport();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", String.format("attachment; filename=laporan-barang.pdf"));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/kartukontak/{tipe}")
    public ResponseEntity<InputStreamResource> getlaporankontak(@PathVariable int tipe) throws FileNotFoundException, JRException {
        byte[] bytes = reportservice.kontakReport(tipe);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", String.format("attachment; filename=laporan-kartu-kontak.pdf"));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/penjualan")
    public ResponseEntity<InputStreamResource> getpenjualan() throws FileNotFoundException, JRException {
        byte[] bytes = reportservice.penjualanReport();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", String.format("attachment; filename=laporan-penjualan.pdf"));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/penjualan-detail")
    public ResponseEntity<InputStreamResource> getpenjualanDetail() throws FileNotFoundException, JRException {
        byte[] bytes = reportservice.penjualanDetailReport();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", String.format("attachment; filename=laporan-penjualan-detail.pdf"));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/pembelian")
    public ResponseEntity<InputStreamResource> getpenmbelian() throws FileNotFoundException, JRException {
        byte[] bytes = reportservice.pembelianReport();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", String.format("attachment; filename=laporan-pembelian.pdf"));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/pembelian-detail")
    public ResponseEntity<InputStreamResource> getpembelianDetail() throws FileNotFoundException, JRException {
        byte[] bytes = reportservice.pembelianDetailReport();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", String.format("attachment; filename=laporan-pembelian-detail.pdf"));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

}
