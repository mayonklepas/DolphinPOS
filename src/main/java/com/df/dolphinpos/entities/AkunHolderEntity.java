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
@Table(name = "akun_holder")
public class AkunHolderEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private UUID idOutlet;
    private String namaAkunHolder;
    private String deskripsiAkunHolder;
    @Column(nullable = true)
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

    public String getNamaAkunHolder() {
        return namaAkunHolder;
    }

    public void setNamaAkunHolder(String namaAkunHolder) {
        this.namaAkunHolder = namaAkunHolder;
    }

    public String getDeskripsiAkunHolder() {
        return deskripsiAkunHolder;
    }

    public void setDeskripsiAkunHolder(String deskripsiAkunHolder) {
        this.deskripsiAkunHolder = deskripsiAkunHolder;
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
