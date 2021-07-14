/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.service.ReportService;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
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

    @GetMapping("/barang/{idOutlet}")
    public ResponseEntity<InputStreamResource> getlaporanstok(@PathVariable UUID idOutlet) throws FileNotFoundException, JRException, IOException {
        byte[] bytes = reportservice.barangReport(idOutlet);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=laporan-penjualan-detail.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/kartukontak/{idOutlet}/{tipe}")
    public ResponseEntity<InputStreamResource> getlaporankontak(@PathVariable UUID idOutlet, @PathVariable int tipe) throws FileNotFoundException, JRException, IOException {
        byte[] bytes = reportservice.kontakReport(idOutlet, tipe);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=laporan-penjualan-detail.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/penjualan/{idOutlet}")
    public ResponseEntity<InputStreamResource> getpenjualan(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.penjualanReport(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=laporan-penjualan-detail.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/penjualan-detail/{idOutlet}")
    public ResponseEntity<InputStreamResource> getpenjualanDetail(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.penjualanDetailReport(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=laporan-penjualan-detail.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/pembelian/{idOutlet}")
    public ResponseEntity<InputStreamResource> getpenmbelian(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.pembelianReport(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=laporan-penjualan-detail.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/pembelian-detail/{idOutlet}")
    public ResponseEntity<InputStreamResource> getpembelianDetail(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.pembelianDetailReport(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=laporan-penjualan-detail.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/catatan/{idOutlet}")
    public ResponseEntity<InputStreamResource> getCatatan(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.catatanReport(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=laporan-catatan.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/faktur/{id}/{idOutlet}")
    public ResponseEntity<InputStreamResource> getfakturpenjualan(@PathVariable UUID id, @PathVariable UUID idOutlet) throws FileNotFoundException, JRException, ParseException, IOException {
        byte[] bytes = reportservice.fakturPenjualan(id, idOutlet);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=faktur-penjualan.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/struk/{id}/{idOutlet}")
    public ResponseEntity<InputStreamResource> getstruk(@PathVariable UUID id, @PathVariable UUID idOutlet) throws FileNotFoundException, JRException, ParseException, IOException {
        byte[] bytes = reportservice.struk(id, idOutlet);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=struk-penjualan.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/penjualan-margin/{idOutlet}")
    public ResponseEntity<InputStreamResource> getpenjualanMargin(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.penjualanMarginReport(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=laporan-penjualan-margin.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/bukubesar/{idOutlet}")
    public ResponseEntity<InputStreamResource> getBukuBesar(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.bukuBesar(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=bukubesar.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/neraca-saldo/{idOutlet}")
    public ResponseEntity<InputStreamResource> getneracaSaldo(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.neracaSaldo(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=neraca-saldo.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/neraca/{idOutlet}")
    public ResponseEntity<InputStreamResource> getneraca(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.neraca(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=neraca-saldo.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

    @GetMapping("/laba-rugi/{idOutlet}")
    public ResponseEntity<InputStreamResource> getlabaRugi(@PathVariable UUID idOutlet, @RequestParam String dari, @RequestParam String hingga) throws FileNotFoundException, JRException, ParseException, IOException {
        Date tanggalDari = new SimpleDateFormat("yyyy-MM-dd").parse(dari);
        Date tanggalHingga = new SimpleDateFormat("yyyy-MM-dd").parse(hingga);
        byte[] bytes = reportservice.labaRugi(idOutlet, tanggalDari, tanggalHingga);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=neraca-saldo.pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(resource);
    }

}
