/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.ChartDto;
import com.df.dolphinpos.dto.PembelianMasterListDTO;
import com.df.dolphinpos.dto.PembelianReportDTO;
import com.df.dolphinpos.dto.PenjualanReportDTO;
import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Minami
 */
public interface PembelianMasterRepository extends PagingAndSortingRepository<PembelianMasterEntity, UUID> {
    
    @Query("SELECT new com.df.dolphinpos.dto.PembelianMasterListDTO("
            + "pme.id,pme.idOutlet,pme.tanggalPembelian,pme.kodePembelianMaster,"
            + "pme.status,pme.deskripsi,pme.totalBelanja,pme.isPosting) "
            + "FROM PembelianMasterEntity pme "
            + "WHERE pme.idOutlet = ?1 ")
    Page<PembelianMasterListDTO> findAllPembelian(Pageable page,UUID idOutlet);
    
    @Query("SELECT new com.df.dolphinpos.dto.PembelianMasterListDTO("
            + "pme.id,pme.idOutlet,pme.tanggalPembelian,pme.kodePembelianMaster,"
            + "pme.status,pme.deskripsi,pme.totalBelanja,pme.isPosting) "
            + "FROM PembelianMasterEntity pme "
            + "WHERE pme.idOutlet = ?1 AND lower(pme.kodePembelianMaster) LIKE %?2%")
    Page<PembelianMasterListDTO> findAllPembelianByKey(Pageable page,UUID idOutlet,String keyword);

    Page<PembelianMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<PembelianMasterEntity> findByIdOutletAndId(UUID idOutlet,UUID id);

    Page<PembelianMasterEntity> findByIdOutletAndKodePembelianMasterContainingIgnoreCase(Pageable page, UUID idOutlet, String kodePembelianMaster);

    @Query("SELECT pm FROM PembelianMasterEntity pm WHERE pm.idOutlet=?1")
    List<PembelianMasterEntity> findPembelian(UUID idOutlet);

    @Query("SELECT "
            + "new com.df.dolphinpos.dto.PembelianReportDTO"
            + "(pm.tanggalPembelian, "
            + "pm.kodePembelianMaster, "
            + "pm.deskripsi, "
            + "pm.akunKeuangan.namaAkunKeuangan, "
            + "pm.kartuKontak.namaKontak,"
            + "pm.pengguna.username, "
            + "pm.disc, "
            + "pm.tax,"
            + "pm.totalBelanja) "
            + "FROM "
            + "PembelianMasterEntity pm "
            + "WHERE pm.idOutlet=?1 AND pm.tanggalPembelian >= ?2 AND pm.tanggalPembelian <= ?3 "
            + "ORDER BY pm.dateCreated DESC")
    List<PembelianReportDTO> findPembelianReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga);

    @Query("SELECT pm FROM PembelianMasterEntity pm WHERE pm.idOutlet=?1 AND pm.tanggalPembelian >= ?2 and pm.tanggalPembelian <= ?3 ORDER BY pm.dateCreated DESC")
    List<PembelianMasterEntity> findPembelianReportDetail(UUID idOutlet, Date tanggalDari, Date tanggalHingga);

    @Query("SELECT new com.df.dolphinpos.dto.ChartDto('label',"
            + "SUM(pm.totalBelanja)) "
            + "FROM PembelianMasterEntity pm "
            + "WHERE pm.idOutlet=?1 AND EXTRACT(MONTH FROM pm.tanggalPembelian)= EXTRACT(MONTH FROM CURRENT_DATE)")
    ChartDto findTotalPembelianBulanIni(UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.ChartDto('label',"
            + "SUM(pm.totalBelanja)) "
            + "FROM PembelianMasterEntity pm "
            + "WHERE pm.idOutlet=?1 AND pm.tanggalPembelian = CURRENT_DATE ")
    ChartDto findTotalPembelianHariIni(UUID idOutlet);

}
