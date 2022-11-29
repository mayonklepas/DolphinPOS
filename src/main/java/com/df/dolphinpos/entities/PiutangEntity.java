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
@Table(name = "piutang")
public class PiutangEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false, unique = true)
    private String kode;
    @Column(nullable = false)
    private Date tanggal;
    @Column(nullable = false)
    private String deskripsi;
    @Column(nullable = false)
    private double jumlah;
    @Column(nullable = false)
    private UUID idKartuKontak;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idKartuKontak", nullable = false, updatable = false, insertable = false)
    @JsonIgnoreProperties("pengguna")
    private KartuKontakEntity kartuKontak;

    @Column(nullable = false)
    private UUID idAkunKeuangan;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAkunKeuangan", nullable = false, updatable = false, insertable = false)
    @JsonIgnoreProperties("pengguna")
    private AkunKeuanganEntity akunKeuangan;

    @Column(nullable = false)
    private UUID idAkunKeuanganDebit;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAkunKeuanganDebit", nullable = false, updatable = false, insertable = false)
    @JsonIgnoreProperties("pengguna")
    private AkunKeuanganEntity akunKeuanganDebit;

    @OneToMany(mappedBy = "hutang", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("hutang")
    private List<PembayaranHutangEntity> pembayaranHutang;

    @Column(nullable = false)
    private UUID idPengguna;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPengguna", nullable = false, updatable = false, insertable = false)
    @JsonIgnoreProperties("outlet")
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

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
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

    public void setAkunKeuanganKredit(AkunKeuanganEntity akunKeuanganDebit) {
        this.akunKeuanganDebit = akunKeuanganDebit;
    }

    public List<PembayaranHutangEntity> getPembayaranHutang() {
        return pembayaranHutang;
    }

    public void setPembayaranHutang(List<PembayaranHutangEntity> pembayaranHutang) {
        this.pembayaranHutang = pembayaranHutang;
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
