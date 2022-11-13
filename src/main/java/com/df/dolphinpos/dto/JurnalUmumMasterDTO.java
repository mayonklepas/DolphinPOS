/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.dto;

import java.util.Date;
import java.util.UUID;


/**
 *
 * @author mulyadi
 */
public class JurnalUmumMasterDTO {
    UUID id;
    UUID idOutlet;
    Date tanggalJurnal;
    String noRef;
    Date tanggalRef;
    int isPosting;
    int tipeJurnal;
    double debit;
    double kredit;

    public JurnalUmumMasterDTO(UUID id, UUID idOutlet, Date tanggalJurnal, String noRef, 
            Date tanggalRef, int isPosting, int tipeJurnal, double debit, double kredit) {
        this.id = id;
        this.idOutlet = idOutlet;
        this.tanggalJurnal = tanggalJurnal;
        this.noRef = noRef;
        this.tanggalRef = tanggalRef;
        this.isPosting = isPosting;
        this.tipeJurnal = tipeJurnal;
        this.debit = debit;
        this.kredit = kredit;
    }

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

    public int getIsPosting() {
        return isPosting;
    }

    public void setIsPosting(int isPosting) {
        this.isPosting = isPosting;
    }

    public int getTipeJurnal() {
        return tipeJurnal;
    }

    public void setTipeJurnal(int tipeJurnal) {
        this.tipeJurnal = tipeJurnal;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getKredit() {
        return kredit;
    }

    public void setKredit(double kredit) {
        this.kredit = kredit;
    }
    
    
    
}
