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
import javax.validation.Valid;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Minami
 */
@Entity
@Table(name = "pembelian_master")
public class PembelianMasterEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false, unique = true)
    private String kodePembelianMaster;
    @Column(nullable = false)
    private Date tanggalPembelian;
    @Column(nullable = false)
    private int status;
    @Column(nullable = true)
    private String statusMessage;
    @Column(nullable = true)
    private String deskripsi;
    @Column(nullable = false)
    private double tax;
    @Column(nullable = false)
    private double disc;
    @Column(nullable = false)
    private double totalBelanja;
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

    @OneToMany(mappedBy = "pembelianMaster", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("pembelianMaster")
    private List<PembelianDetailEntity> pembelianDetail;

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

    public String getKodePembelianMaster() {
        return kodePembelianMaster;
    }

    public void setKodePembelianMaster(String kodePembelianMaster) {
        this.kodePembelianMaster = kodePembelianMaster;
    }

    public Date getTanggalPembelian() {
        return tanggalPembelian;
    }

    public void setTanggalPembelian(Date tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
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

    public List<PembelianDetailEntity> getPembelianDetail() {
        return pembelianDetail;
    }

    public void setPembelianDetail(List<PembelianDetailEntity> pembelianDetail) {
        this.pembelianDetail = pembelianDetail;
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

    public int getIsPosting() {
        return isPosting;
    }

    public void setIsPosting(int isPosting) {
        this.isPosting = isPosting;
    }

}
