/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.ChartDto;
import com.df.dolphinpos.dto.PenjualanReportDTO;
import com.df.dolphinpos.dto.ReturPenjualanMasterListDTO;
import com.df.dolphinpos.dto.StrukDto;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
import com.df.dolphinpos.entities.ReturPenjualanMasterEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Minami
 */
@Repository
public interface ReturPenjualanMasterRepository extends PagingAndSortingRepository<ReturPenjualanMasterEntity, UUID> {

    @Query("SELECT new com.df.dolphinpos.dto.ReturPenjualanMasterListDTO("
            + "rpme.id,rpme.idOutlet,rpme.tanggalReturPenjualan,rpme.kodeReturPenjualanMaster,"
            + "rpme.status,rpme.deskripsi,rpme.totalBelanja,rpme.isPosting) "
            + "FROM ReturPenjualanMasterEntity rpme "
            + "WHERE rpme.idOutlet = ?1 ")
    Page<ReturPenjualanMasterListDTO> findAllReturPenjualan(Pageable page, UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.ReturPenjualanMasterListDTO("
            + "rpme.id,rpme.idOutlet,rpme.tanggalReturPenjualan,rpme.kodeReturPenjualanMaster,"
            + "rpme.status,rpme.deskripsi,rpme.totalBelanja,rpme.isPosting) "
            + "FROM ReturPenjualanMasterEntity rpme "
            + "WHERE rpme.idOutlet = ?1 AND lower(rpme.kodeReturPenjualanMaster) LIKE %?2%")
    Page<ReturPenjualanMasterListDTO> findAllReturPenjualanByKey(Pageable page, UUID idOutlet, String keyword);
    
    Page<ReturPenjualanMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<ReturPenjualanMasterEntity> findByIdOutletAndId(UUID id, UUID idOutlet);

    Page<ReturPenjualanMasterEntity> findByIdOutletAndKodeReturPenjualanMasterContainingIgnoreCase(Pageable page, UUID idOutlet, String kodeReturPenjualanMaster);

    @Query("SELECT rpm FROM ReturPenjualanMasterEntity rpm WHERE rpm.idOutlet=?1")
    List<ReturPenjualanMasterEntity> findReturPenjualan(UUID idOutlet);

   
}
