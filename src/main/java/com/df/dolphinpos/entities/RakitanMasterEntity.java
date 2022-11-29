/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author mulyadi
 */
@Entity
@Table(name = "rakitan_master")
public class RakitanMasterEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false)
    private Date tanggalPerakitan;
    @Column(nullable = false)
    private String kodePerakitan;
    @Column(nullable = false)
    private String namaPerakitan;

    @Column(nullable = true)
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
    private double jumlahBarang;
    @Column(nullable = false)
    private double hargaModal;
    
    @Column(nullable = true)
    private String gambar;

    @OneToMany(mappedBy = "rakitanMaster", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("rakitanMaster")
    private List<RakitanDetailEntity> rakitanDetail;

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

    public Date getTanggalPerakitan() {
        return tanggalPerakitan;
    }

    public void setTanggalPerakitan(Date tanggalPerakitan) {
        this.tanggalPerakitan = tanggalPerakitan;
    }

    public String getKodePerakitan() {
        return kodePerakitan;
    }

    public void setKodePerakitan(String kodePerakitan) {
        this.kodePerakitan = kodePerakitan;
    }

    public String getNamaPerakitan() {
        return namaPerakitan;
    }

    public void setNamaPerakitan(String namaPerakitan) {
        this.namaPerakitan = namaPerakitan;
    }

    public BarangEntity getBarang() {
        return barang;
    }

    public void setBarang(BarangEntity barang) {
        this.barang = barang;
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

    public double getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(double jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public double getHargaModal() {
        return hargaModal;
    }

    public void setHargaModal(double hargaModal) {
        this.hargaModal = hargaModal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    
    public List<RakitanDetailEntity> getRakitanDetail() {
        return rakitanDetail;
    }

    public void setRakitanDetail(List<RakitanDetailEntity> rakitanDetail) {
        this.rakitanDetail = rakitanDetail;
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
