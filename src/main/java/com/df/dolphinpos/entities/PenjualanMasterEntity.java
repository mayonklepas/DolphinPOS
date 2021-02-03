/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
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
 * @author Minami
 */
@Entity
@Table(name = "penjualan_master")
public class PenjualanMasterEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private UUID idOutlet;
    private String kodePenjualanMaster;
    private Date tanggalPenjualan;
    private int status;
    private String deskripsi;
    private double tax;

    private double disc;

    private UUID idKartuKontak;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idKartuKontak", nullable = false, updatable = false, insertable = false)
    private KartuKontakEntity kartuKontak;

    private UUID idAkunHolder;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAkunHolder", nullable = false, updatable = false, insertable = false)
    private AkunHolderEntity akunHolder;

    @OneToMany(mappedBy = "penjualanMaster", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("penjualanMaster")
    private List<PenjualanDetailEntity> penjualanDetail;

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

    public String getKodePenjualanMaster() {
        return kodePenjualanMaster;
    }

    public void setKodePenjualanMaster(String kodePenjualanMaster) {
        this.kodePenjualanMaster = kodePenjualanMaster;
    }

    public Date getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggalPenjualan(Date tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDisc() {
        return disc;
    }

    public void setDisc(double disc) {
        this.disc = disc;
    }

    public UUID getIdKartuKontak() {
        return idKartuKontak;
    }

    public void setIdKartuKontak(UUID idKartuKontak) {
        this.idKartuKontak = idKartuKontak;
    }

    public KartuKontakEntity getKartuKontak() {
        return kartuKontak;
    }

    public void setKartuKontak(KartuKontakEntity kartuKontak) {
        this.kartuKontak = kartuKontak;
    }

    
    public UUID getIdAkunHolder() {
        return idAkunHolder;
    }

    public void setIdAkunHolder(UUID idAkunHolder) {
        this.idAkunHolder = idAkunHolder;
    }

    public AkunHolderEntity getAkunHolder() {
        return akunHolder;
    }

    public void setAkunHolder(AkunHolderEntity akunHolder) {
        this.akunHolder = akunHolder;
    }

    public List<PenjualanDetailEntity> getPenjualanDetail() {
        return penjualanDetail;
    }

    public void setPenjualanDetail(List<PenjualanDetailEntity> penjualanDetail) {
        this.penjualanDetail = penjualanDetail;
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
