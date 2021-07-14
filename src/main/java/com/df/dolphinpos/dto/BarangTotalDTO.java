/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

/**
 *
 * @author Minami
 */
public class BarangTotalDTO {
    double totalHargaBeli;
    double totalHargaJual;
    long totalBarang;

    public BarangTotalDTO(double totalHargaBeli, double totalHargaJual, long totalBarang) {
        this.totalHargaBeli = totalHargaBeli;
        this.totalHargaJual = totalHargaJual;
        this.totalBarang = totalBarang;
    }
    
    

    public double getTotalHargaBeli() {
        return totalHargaBeli;
    }

    public void setTotalHargaBeli(double totalHargaBeli) {
        this.totalHargaBeli = totalHargaBeli;
    }

    public double getTotalHargaJual() {
        return totalHargaJual;
    }

    public void setTotalHargaJual(double totalHargaJual) {
        this.totalHargaJual = totalHargaJual;
    }

    public long getTotalBarang() {
        return totalBarang;
    }

    public void setTotalBarang(long totalBarang) {
        this.totalBarang = totalBarang;
    }

  

    
    
}
