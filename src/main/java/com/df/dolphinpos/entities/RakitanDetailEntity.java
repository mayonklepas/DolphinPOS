/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * @author mulyadi
 */
@Entity
@Table(name = "rakitan_detail")
public class RakitanDetailEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false)
    private UUID idRakitanMaster;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idRakitanMaster", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("rakitanDetail")
    private RakitanMasterEntity rakitanMaster;

    @Column(nullable = false)
    private UUID idBarang;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idBarang", nullable = false, updatable = false, insertable = false)
    private BarangEntity barang;
    @Column(nullable = false)
    private double jumlahIsi;

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

    public UUID getIdRakitanMaster() {
        return idRakitanMaster;
    }

    public void setIdRakitanMaster(UUID idRakitanMaster) {
        this.idRakitanMaster = idRakitanMaster;
    }

    public RakitanMasterEntity getRakitanMaster() {
        return rakitanMaster;
    }

    public void setRakitanMaster(RakitanMasterEntity rakitanMaster) {
        this.rakitanMaster = rakitanMaster;
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

    public double getJumlahIsi() {
        return jumlahIsi;
    }

    public void setJumlahIsi(double jumlahIsi) {
        this.jumlahIsi = jumlahIsi;
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
