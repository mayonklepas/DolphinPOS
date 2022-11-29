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
@Table(name = "pembayaran_piutang")
public class PembayaranPiutangEntity {

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
    private UUID idPiutang;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPiutang", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("pembayaranPiutang")
    private PiutangEntity piutang;

    @Column(nullable = false)
    private UUID idAkunKeuangan;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAkunKeuangan", nullable = false, updatable = false, insertable = false)
    private AkunKeuanganEntity akunKeuangan;

    @Column(nullable = false)
    private UUID idAkunKeuanganKredit;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAkunKeuanganKredit", nullable = false, updatable = false, insertable = false)
    private AkunKeuanganEntity akunKeuanganKredit;

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

    public UUID getIdPiutang() {
        return idPiutang;
    }

    public void setIdPiutang(UUID idPiutang) {
        this.idPiutang = idPiutang;
    }

    public PiutangEntity getPiutang() {
        return piutang;
    }

    public void setHutang(PiutangEntity piutang) {
        this.piutang = piutang;
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

    public UUID getIdAkunKeuanganKredit() {
        return idAkunKeuanganKredit;
    }

    public void setIdAkunKeuanganKredit(UUID idAkunKeuanganKredit) {
        this.idAkunKeuanganKredit = idAkunKeuanganKredit;
    }

    public AkunKeuanganEntity getAkunKeuanganKredit() {
        return akunKeuanganKredit;
    }

    public void setAkunKeuanganKredit(AkunKeuanganEntity akunKeuanganKredit) {
        this.akunKeuanganKredit = akunKeuanganKredit;
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
