/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.entities;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;


/**
 *
 * @author Minami
 */
@Entity
@Table(name = "barang")
public class BarangEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private UUID idOutlet;
    private String kodeBarang;
    private String namaBarang;
    private String satuanBarang;
    private double jumlahBarang;
    private double hargaBeli;
    private double hargaJual;
    private int tipeBarang;
    private String kodeResep;
    
    @Column(nullable = true)
    private UUID kodeDiskon;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "kodeDiskon", nullable = true, insertable = false, updatable = false)
    private DiskonEntity diskon;

    @Column(nullable = true)
    private UUID idPengguna;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPengguna", nullable = false, updatable = false, insertable = false)
    private PenggunaEntity pengguna;
    
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreated;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdOutlet() {
        return idOutlet;
    }

    public void setIdOutlet(UUID idOutlet) {
        this.idOutlet = idOutlet;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getSatuanBarang() {
        return satuanBarang;
    }

    public void setSatuanBarang(String satuanBarang) {
        this.satuanBarang = satuanBarang;
    }

    public double getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(double jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public int getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(int tipeBarang) {
        this.tipeBarang = tipeBarang;
    }

    public String getKodeResep() {
        return kodeResep;
    }

    public void setKodeResep(String kodeResep) {
        this.kodeResep = kodeResep;
    }
        

    public UUID getKodeDiskon() {
        return kodeDiskon;
    }

    public void setKodeDiskon(UUID kodeDiskon) {
        this.kodeDiskon = kodeDiskon;
    }

    public DiskonEntity getDiskon() {
        return diskon;
    }

    public void setDiskon(DiskonEntity diskon) {
        this.diskon = diskon;
    }

    public UUID getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(UUID idPengguna) {
        this.idPengguna = idPengguna;
    }

    public PenggunaEntity getPengguna() {
        return pengguna;
    }

    public void setPengguna(PenggunaEntity pengguna) {
        this.pengguna = pengguna;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    

}
