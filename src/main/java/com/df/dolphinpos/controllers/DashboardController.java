/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.dto.ChartDto;
import com.df.dolphinpos.dto.ChartDtoInf;
import com.df.dolphinpos.dto.DashboardResultDto;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import com.df.dolphinpos.repositories.CatatanRepository;
import com.df.dolphinpos.repositories.PembelianMasterRepository;
import com.df.dolphinpos.repositories.PenjualanDetailRepository;
import com.df.dolphinpos.repositories.PenjualanMasterRepository;
import com.df.dolphinpos.service.DashboardService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    PenjualanMasterRepository penjualanRepo;

    @Autowired
    PembelianMasterRepository pembelianRepo;

    @Autowired
    CatatanRepository catatanRepo;
    
    @Autowired
    DashboardService dashService;
    
    @Autowired
    PenjualanDetailRepository penjualanDetailRepo;

    @GetMapping("/chart/{idOutlet}")
    public DashboardResultDto getchart(@PathVariable UUID idOutlet) {
        List<ChartDto> chartPenjualan = penjualanRepo.findChartPenjualan(idOutlet);
        List<ChartDto> chartPembelian = pembelianRepo.findChartPembelian(idOutlet);
        List<ChartDto> chartTopSelling = penjualanDetailRepo.getTopselling(idOutlet);
        ChartDto totalPenjualan = penjualanRepo.findTotalPenjualan(idOutlet);
        ChartDto totalPembelian = pembelianRepo.findTotalPembelian(idOutlet);
        ChartDto totalCatatanPendapatan = catatanRepo.findTotalPendapatanCatatan(idOutlet);
        ChartDto totalCatatanPengeluaran = catatanRepo.findTotalPengeluaranCatatan(idOutlet);
        DashboardResultDto drd = new DashboardResultDto();
        drd.setChartPembelian(chartPembelian);
        drd.setChartPenjualan(chartPenjualan);
        drd.setChartTopSelling(chartTopSelling);
        drd.setTotalPembelian(totalPembelian);
        drd.setTotalPenjualan(totalPenjualan);
        drd.setTotalCatatanPendapatan(totalCatatanPendapatan);
        drd.setTotalCatatanPengeluaran(totalCatatanPengeluaran);
        return drd;
    }

}
