/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import java.util.List;

/**
 *
 * @author Minami
 */
public class DashboardResultDto {

    Object chartPenjualan;
    Object chartPembelian;
    Object chartTopSelling;
    Object totalPenjualan;
    Object totalPembelian;
    Object totalCatatanPendapatan;
    Object totalCatatanPengeluaran;

    public Object getChartPenjualan() {
        return chartPenjualan;
    }

    public void setChartPenjualan(Object chartPenjualan) {
        this.chartPenjualan = chartPenjualan;
    }

    public Object getChartPembelian() {
        return chartPembelian;
    }

    public void setChartPembelian(Object chartPembelian) {
        this.chartPembelian = chartPembelian;
    }

    public Object getChartTopSelling() {
        return chartTopSelling;
    }

    public void setChartTopSelling(Object chartTopSelling) {
        this.chartTopSelling = chartTopSelling;
    }
    

    public Object getTotalPenjualan() {
        return totalPenjualan;
    }

    public void setTotalPenjualan(Object totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }

    public Object getTotalPembelian() {
        return totalPembelian;
    }

    public void setTotalPembelian(Object totalPembelian) {
        this.totalPembelian = totalPembelian;
    }

    public Object getTotalCatatanPendapatan() {
        return totalCatatanPendapatan;
    }

    public void setTotalCatatanPendapatan(Object totalCatatanPendapatan) {
        this.totalCatatanPendapatan = totalCatatanPendapatan;
    }

    public Object getTotalCatatanPengeluaran() {
        return totalCatatanPengeluaran;
    }

    public void setTotalCatatanPengeluaran(Object totalCatatanPengeluaran) {
        this.totalCatatanPengeluaran = totalCatatanPengeluaran;
    }

                
}
