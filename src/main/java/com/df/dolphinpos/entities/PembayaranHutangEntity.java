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
@Table(name = "pembayaran_hutang")
public class PembayaranHutangEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;

    @Column(nullable = true)
    private String kodePembayaran;

    @Column(nullable = false)
    private UUID idHutang;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idHutang", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("pembayaranHutang")
    private HutangEntity hutang;

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

    @Column(nullable = false)
    private Date tanggal;
    @Column(nullable = true)
    private String deskripsi;
    @Column(nullable = false)
    private double jumlah;
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

    public String getKodePembayaran() {
        return kodePembayaran;
    }

    public void setKodePembayaran(String kodePembayaran) {
        this.kodePembayaran = kodePembayaran;
    }

    public UUID getIdHutang() {
        return idHutang;
    }

    public void setIdHutang(UUID idHutang) {
        this.idHutang = idHutang;
    }

    public HutangEntity getHutang() {
        return hutang;
    }

    public void setHutang(HutangEntity hutang) {
        this.hutang = hutang;
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

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
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
