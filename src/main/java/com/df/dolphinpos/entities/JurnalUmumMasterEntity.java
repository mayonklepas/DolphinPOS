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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Minami
 */
@Entity
@Table(name = "jurnal_umum_master")
public class JurnalUmumMasterEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID idOutlet;
    @Column(nullable = false)
    private UUID idPengguna;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idPengguna", nullable = false, updatable = false, insertable = false)
    private PenggunaEntity pengguna;
    @Column(nullable = false)
    private Date tanggalJurnal;
    @Column(nullable = false)
    private String noRef;
    @Column(nullable = false)
    private Date tanggalRef;
    private int tipeJurnal;
    @Column(nullable = true)
    private String deskripsi;
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreated;
    
    
    @OneToMany(mappedBy = "jurnalUmumMaster",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("jurnalUmumMaster")
    private List<JurnalUmumDetailEntity> jurnalUmumDetail;

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

    public Date getTanggalJurnal() {
        return tanggalJurnal;
    }

    public void setTanggalJurnal(Date tanggalJurnal) {
        this.tanggalJurnal = tanggalJurnal;
    }

    public String getNoRef() {
        return noRef;
    }

    public void setNoRef(String noRef) {
        this.noRef = noRef;
    }

    public Date getTanggalRef() {
        return tanggalRef;
    }

    public void setTanggalRef(Date tanggalRef) {
        this.tanggalRef = tanggalRef;
    }

    public int getTipeJurnal() {
        return tipeJurnal;
    }

    public void setTipeJurnal(int tipeJurnal) {
        this.tipeJurnal = tipeJurnal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<JurnalUmumDetailEntity> getJurnalUmumDetail() {
        return jurnalUmumDetail;
    }

    public void setJurnalUmumDetail(List<JurnalUmumDetailEntity> jurnalUmumDetail) {
        this.jurnalUmumDetail = jurnalUmumDetail;
    }

    
    
    

}
