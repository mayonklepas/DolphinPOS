/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
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
@Table(name = "retur_penjualan_detail")
public class ReturPenjualanDetailEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false)
    private Date tanggalTransaksi;
    @Column(nullable = false)
    private UUID idReturPenjualanMaster;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idReturPenjualanMaster", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("returPenjualanDetail")
    private ReturPenjualanMasterEntity returPenjualanMaster;
    @Column(nullable = true)
    private UUID idBarang;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idBarang", nullable = false, insertable = false, updatable = false)
    private BarangEntity barang;
    @Column(nullable = false)
    private double jumlahJualRetur;
    @Column(nullable = true)
    private double hargaBeliJual;
    @Column(nullable = false)
    private double hargaJualJual;
    @Column(nullable = false)
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

    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(Date tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public UUID getIdReturPenjualanMaster() {
        return idReturPenjualanMaster;
    }

    public void setIdReturPenjualanMaster(UUID idReturPenjualanMaster) {
        this.idReturPenjualanMaster = idReturPenjualanMaster;
    }

    public ReturPenjualanMasterEntity getReturPenjualanMaster() {
        return returPenjualanMaster;
    }

    public void setReturPenjualanMaster(ReturPenjualanMasterEntity returPenjualanMaster) {
        this.returPenjualanMaster = returPenjualanMaster;
    }

    public UUID getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(UUID idBarang) {
        this.idBarang = idBarang;
    }

    public BarangEntity getBarang() {
        return barang;
    }

    public void setBarang(BarangEntity barang) {
        this.barang = barang;
    }

    public double getJumlahJualRetur() {
        return jumlahJualRetur;
    }

    public void setJumlahJualRetur(double jumlahJualRetur) {
        this.jumlahJualRetur = jumlahJualRetur;
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

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

   
    
   

}
