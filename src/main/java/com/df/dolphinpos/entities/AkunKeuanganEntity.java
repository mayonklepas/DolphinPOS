/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.entities;

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
import org.hibernate.annotations.Type;

/**
 *
 * @author Minami
 */
@Entity
@Table(name = "akun_keuangan")
public class AkunKeuanganEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false,unique = true)
    private String kodeAkunKeuangan;
    @Column(nullable = false)
    private String namaAkunKeuangan;
    @Column(nullable = false)
    private double openingBalance;
    @Column(nullable = false)
    private double currentBalance;
    @Column(nullable = false)
    private int tipeAkun;
    @Column(nullable = false)
    private int groupAkun;
    private String deskripsiAkunKeuangan;
    @Column(nullable = false)
    private UUID idPengguna;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPengguna",nullable = false,updatable = false,insertable = false)
    private PenggunaEntity pengguna;
    
    @Column(nullable = false,updatable = false,insertable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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

    public String getKodeAkunKeuangan() {
        return kodeAkunKeuangan;
    }

    public void setKodeAkunKeuangan(String kodeAkunKeuangan) {
        this.kodeAkunKeuangan = kodeAkunKeuangan;
    }

    public String getNamaAkunKeuangan() {
        return namaAkunKeuangan;
    }

    public void setNamaAkunKeuangan(String namaAkunKeuangan) {
        this.namaAkunKeuangan = namaAkunKeuangan;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getTipeAkun() {
        return tipeAkun;
    }

    public void setTipeAkun(int tipeAkun) {
        this.tipeAkun = tipeAkun;
    }

    public int getGroupAkun() {
        return groupAkun;
    }

    public void setGroupAkun(int groupAkun) {
        this.groupAkun = groupAkun;
    }

    public String getDeskripsiAkunKeuangan() {
        return deskripsiAkunKeuangan;
    }

    public void setDeskripsiAkunKeuangan(String deskripsiAkunKeuangan) {
        this.deskripsiAkunKeuangan = deskripsiAkunKeuangan;
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
