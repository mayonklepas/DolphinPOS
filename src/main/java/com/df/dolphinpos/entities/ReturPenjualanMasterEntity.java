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
@Table(name = "retur_penjualan_master")
public class ReturPenjualanMasterEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = true)
    private String kodeReturPenjualanMaster;
    @Column(nullable = false)
    private Date tanggalReturPenjualan;
    @Column(nullable = false)
    private int status;
    @Column(nullable = true)
    private String deskripsi;
    @Column(nullable = false)
    private double tax;
    @Column(nullable = true)
    private double totalBelanja;
    @Column(nullable = false)
    private double disc;
    @Column(nullable = false)
    private UUID idKartuKontak;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idKartuKontak", nullable = false, updatable = false, insertable = false)
    private KartuKontakEntity kartuKontak;

    @Column(nullable = false)
    private UUID idAkunKeuangan;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAkunKeuangan", nullable = false, updatable = false, insertable = false)
    private AkunKeuanganEntity akunKeuangan;

    @Column(nullable = false)
    private UUID idAkunKeuanganDebit;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAkunKeuanganDebit", nullable = false, updatable = false, insertable = false)
    private AkunKeuanganEntity akunKeuanganDebit;

    @OneToMany(mappedBy = "returPenjualanMaster", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("returPenjualanMaster")
    private List<ReturPenjualanDetailEntity> returPenjualanDetail;

    @Column(nullable = false)
    private UUID idPengguna;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPengguna", nullable = false, updatable = false, insertable = false)
    private PenggunaEntity pengguna;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreated;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int isPosting;

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

    public String getKodeReturPenjualanMaster() {
        return kodeReturPenjualanMaster;
    }

    public void setKodeReturPenjualanMaster(String kodeReturPenjualanMaster) {
        this.kodeReturPenjualanMaster = kodeReturPenjualanMaster;
    }

    public Date getTanggalReturPenjualan() {
        return tanggalReturPenjualan;
    }

    public void setTanggalReturPenjualan(Date tanggalReturPenjualan) {
        this.tanggalReturPenjualan = tanggalReturPenjualan;
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

    public double getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(double totalBelanja) {
        this.totalBelanja = totalBelanja;
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

    public UUID getIdAkunKeuangan() {
        return idAkunKeuangan;
    }

    public void setIdAkunKeuangan(UUID idAkunKeuangan) {
        this.idAkunKeuangan = idAkunKeuangan;
    }

    public AkunKeuanganEntity getAkunKeuangan() {
        return akunKeuangan;
    }

    public void setAkunKeuangan(AkunKeuanganEntity akunKeuangan) {
        this.akunKeuangan = akunKeuangan;
    }

    public UUID getIdAkunKeuanganDebit() {
        return idAkunKeuanganDebit;
    }

    public void setIdAkunKeuanganDebit(UUID idAkunKeuanganDebit) {
        this.idAkunKeuanganDebit = idAkunKeuanganDebit;
    }

    public AkunKeuanganEntity getAkunKeuanganDebit() {
        return akunKeuanganDebit;
    }

    public void setAkunKeuanganDebit(AkunKeuanganEntity akunKeuanganDebit) {
        this.akunKeuanganDebit = akunKeuanganDebit;
    }

    public List<ReturPenjualanDetailEntity> getReturPenjualanDetail() {
        return returPenjualanDetail;
    }

    public void setReturPenjualanDetail(List<ReturPenjualanDetailEntity> returPenjualanDetail) {
        this.returPenjualanDetail = returPenjualanDetail;
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

    public int getIsPosting() {
        return isPosting;
    }

    public void setIsPosting(int isPosting) {
        this.isPosting = isPosting;
    }

}
