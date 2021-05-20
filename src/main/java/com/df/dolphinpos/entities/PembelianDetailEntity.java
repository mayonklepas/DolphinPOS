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
@Table(name = "pembelian_detail")
public class PembelianDetailEntity {

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
    private UUID idPembelianMaster;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPembelianMaster", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("pembelianDetail")
    private PembelianMasterEntity pembelianMaster;
    @Column(nullable = false)
    private UUID idBarang;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idBarang", nullable = false, insertable = false, updatable = false)
    private BarangEntity barang;
    @Column(nullable = false)
    private String kodeBarang;
    @Column(nullable = false)
    private String namaBarang;
    @Column(nullable = false)
    private String satuanBarang;
    @Column(nullable = false)
    private int tipeBarang;
    @Column(nullable = false)
    private double jumlahBeli;
    @Column(nullable = false)
    private double hargaBeliBeli;
    @Column(nullable = false)
    private double hargaJualBeli;
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
    
    

    public UUID getIdPembelianMaster() {
        return idPembelianMaster;
    }

    public void setIdPembelianMaster(UUID idPembelianMaster) {
        this.idPembelianMaster = idPembelianMaster;
    }

    public UUID getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(UUID idBarang) {
        this.idBarang = idBarang;
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

    public int getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(int tipeBarang) {
        this.tipeBarang = tipeBarang;
    }

    public PembelianMasterEntity getPembelianMaster() {
        return pembelianMaster;
    }

    public void setPembelianMaster(PembelianMasterEntity pembelianMaster) {
        this.pembelianMaster = pembelianMaster;
    }

    public BarangEntity getBarang() {
        return barang;
    }

    public void setBarang(BarangEntity barang) {
        this.barang = barang;
    }

    public double getJumlahBeli() {
        return jumlahBeli;
    }

    public void setJumlahBeli(double jumlahBeli) {
        this.jumlahBeli = jumlahBeli;
    }

    public double getHargaBeliBeli() {
        return hargaBeliBeli;
    }

    public void setHargaBeliBeli(double hargaBeliBeli) {
        this.hargaBeliBeli = hargaBeliBeli;
    }

    public double getHargaJualBeli() {
        return hargaJualBeli;
    }

    public void setHargaJualBeli(double hargaJualBeli) {
        this.hargaJualBeli = hargaJualBeli;
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
