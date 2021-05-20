/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.entities;

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

/**
 *
 * @author Minami
 */
@Entity
@Table(name = "koreksi_stok")
public class KoreksiStokEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name="id",nullable = false,updatable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false)
    private UUID idPengguna;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPengguna",nullable = false,insertable = false,updatable = false)
    private PenggunaEntity pengguna;
    @Column(nullable = false)
    private Date tanggalKoreksi;
    @Column(nullable = false)
    private UUID idBarang;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="idBarang",nullable = false,insertable = false,updatable = false)
    private BarangEntity barang;
    @Column(nullable = false)
    private int tipeKoreksi;
    @Column(nullable = false)
    private int jumlahKoreksi;
    @Column(nullable = false)
    private String deskripsi;
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

    public Date getTanggalKoreksi() {
        return tanggalKoreksi;
    }

    public void setTanggalKoreksi(Date tanggalKoreksi) {
        this.tanggalKoreksi = tanggalKoreksi;
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

    public int getTipeKoreksi() {
        return tipeKoreksi;
    }

    public void setTipeKoreksi(int tipeKoreksi) {
        this.tipeKoreksi = tipeKoreksi;
    }

    public int getJumlahKoreksi() {
        return jumlahKoreksi;
    }

    public void setJumlahKoreksi(int jumlahKoreksi) {
        this.jumlahKoreksi = jumlahKoreksi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    
    
}
