/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.entities;

import java.sql.Timestamp;
import java.sql.Date;
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
@Table(name = "diskon")
public class DiskonEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false)
    private String namaDiskon;
    @Column(nullable = false)
    private double minimalPembelianSatu;
    @Column(nullable = false)
    private double minimalPembelianDua;
    @Column(nullable = true)
    private int tipeDiskon;
    @Column(nullable = false)
    private Date tanggalBerlakuHingga;
    @Column(nullable = false)
    private double nominalDiskonSatu;
    @Column(nullable = false)
    private double nominalDiskonDua;
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

    public String getNamaDiskon() {
        return namaDiskon;
    }

    public void setNamaDiskon(String namaDiskon) {
        this.namaDiskon = namaDiskon;
    }

    public double getMinimalPembelianSatu() {
        return minimalPembelianSatu;
    }

    public void setMinimalPembelianSatu(double minimalPembelianSatu) {
        this.minimalPembelianSatu = minimalPembelianSatu;
    }

    public double getMinimalPembelianDua() {
        return minimalPembelianDua;
    }

    public void setMinimalPembelianDua(double minimalPembelianDua) {
        this.minimalPembelianDua = minimalPembelianDua;
    }

    public int getTipeDiskon() {
        return tipeDiskon;
    }

    public void setTipeDiskon(int tipeDiskon) {
        this.tipeDiskon = tipeDiskon;
    }

    public Date getTanggalBerlakuHingga() {
        return tanggalBerlakuHingga;
    }

    public void setTanggalBerlakuHingga(Date tanggalBerlakuHingga) {
        this.tanggalBerlakuHingga = tanggalBerlakuHingga;
    }

    public double getNominalDiskonSatu() {
        return nominalDiskonSatu;
    }

    public void setNominalDiskonSatu(double nominalDiskonSatu) {
        this.nominalDiskonSatu = nominalDiskonSatu;
    }

    public double getNominalDiskonDua() {
        return nominalDiskonDua;
    }

    public void setNominalDiskonDua(double nominalDiskonDua) {
        this.nominalDiskonDua = nominalDiskonDua;
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
