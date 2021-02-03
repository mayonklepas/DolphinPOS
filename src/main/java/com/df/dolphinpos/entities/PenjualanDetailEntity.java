/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Minami
 */
@Entity
@Table(name = "penjualan_detail")
public class PenjualanDetailEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private UUID idOutlet;
    @Column(nullable = false)
    private UUID idPenjualanMaster;
    @Column(nullable = true)
    private UUID idBarang;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPenjualanMaster", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("penjualanDetail")
    private PenjualanMasterEntity penjualanMaster;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idBarang", nullable = false, insertable = false, updatable = false)
    private BarangEntity barang;

    private double jumlahJual;
    private double hargaBeliJual;
    private double hargaJualJual;
    private double disc;


    @Column(nullable = false)
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

    public UUID getIdPenjualanMaster() {
        return idPenjualanMaster;
    }

    public void setIdPenjualanMaster(UUID idPenjualanMaster) {
        this.idPenjualanMaster = idPenjualanMaster;
    }

    public UUID getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(UUID idBarang) {
        this.idBarang = idBarang;
    }

    public PenjualanMasterEntity getPenjualanMaster() {
        return penjualanMaster;
    }

    public void setPenjualanMaster(PenjualanMasterEntity penjualanMaster) {
        this.penjualanMaster = penjualanMaster;
    }

    public BarangEntity getBarang() {
        return barang;
    }

    public void setBarang(BarangEntity barang) {
        this.barang = barang;
    }

    public double getJumlahJual() {
        return jumlahJual;
    }

    public void setJumlahJual(double jumlahJual) {
        this.jumlahJual = jumlahJual;
    }

    public double getHargaBeliJual() {
        return hargaBeliJual;
    }

    public void setHargaBeliJual(double hargaBeliJual) {
        this.hargaBeliJual = hargaBeliJual;
    }

    public double getHargaJualJual() {
        return hargaJualJual;
    }

    public void setHargaJualJual(double hargaJualJual) {
        this.hargaJualJual = hargaJualJual;
    }

    public double getDisc() {
        return disc;
    }

    public void setDisc(double disc) {
        this.disc = disc;
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
