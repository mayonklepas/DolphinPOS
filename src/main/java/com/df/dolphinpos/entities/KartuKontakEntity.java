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

/**
 *
 * @author Minami
 */
@Entity
@Table(name = "kartu_kontak")
public class KartuKontakEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private UUID idOutlet;
    private String namaKontak;
    private String alamatKontak;
    private String emailKontak;
    private String nohpKontak;
    private String deskripsi;
    private int tipeKontak;
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

    public String getNamaKontak() {
        return namaKontak;
    }

    public void setNamaKontak(String namaKontak) {
        this.namaKontak = namaKontak;
    }

    public String getAlamatKontak() {
        return alamatKontak;
    }

    public void setAlamatKontak(String alamatKontak) {
        this.alamatKontak = alamatKontak;
    }

    public String getEmailKontak() {
        return emailKontak;
    }

    public void setEmailKontak(String emailKontak) {
        this.emailKontak = emailKontak;
    }

    public String getNohpKontak() {
        return nohpKontak;
    }

    public void setNohpKontak(String nohpKontak) {
        this.nohpKontak = nohpKontak;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getTipeKontak() {
        return tipeKontak;
    }

    public void setTipeKontak(int tipeKontak) {
        this.tipeKontak = tipeKontak;
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
