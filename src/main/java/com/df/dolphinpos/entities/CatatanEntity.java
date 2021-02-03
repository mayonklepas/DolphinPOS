   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.entities;

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
@Table(name = "catatan")
public class CatatanEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private UUID idOutlet;
    private Date tanggalCatatan;
    private int tipeCatatan;
    private String deskripsi;
    private double jumlah;
    @Column(nullable = true)
    private UUID idAkunHolder;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAkunHolder", nullable = false, insertable = false, updatable = false)
    private AkunHolderEntity akunHolder;

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

    public Date getTanggalCatatan() {
        return tanggalCatatan;
    }

    public void setTanggalCatatan(Date tanggalCatatan) {
        this.tanggalCatatan = tanggalCatatan;
    }

    public int getTipeCatatan() {
        return tipeCatatan;
    }

    public void setTipeCatatan(int tipeCatatan) {
        this.tipeCatatan = tipeCatatan;
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
