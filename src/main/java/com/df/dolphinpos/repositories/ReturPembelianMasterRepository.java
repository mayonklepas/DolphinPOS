/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.ReturPembelianMasterListDTO;
import com.df.dolphinpos.entities.ReturPembelianMasterEntity;
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
public interface ReturPembelianMasterRepository extends PagingAndSortingRepository<ReturPembelianMasterEntity, UUID> {

    @Query("SELECT new com.df.dolphinpos.dto.ReturPembelianMasterListDTO("
            + "rpme.id,rpme.idOutlet,rpme.tanggalReturPembelian,rpme.kodeReturPembelianMaster,"
            + "rpme.status,rpme.deskripsi,rpme.totalBelanja,rpme.isPosting) "
            + "FROM ReturPembelianMasterEntity rpme "
            + "WHERE rpme.idOutlet = ?1 ")
    Page<ReturPembelianMasterListDTO> findAllReturPembelian(Pageable page, UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.ReturPembelianMasterListDTO("
            + "rpme.id,rpme.idOutlet,rpme.tanggalReturPembelian,rpme.kodeReturPembelianMaster,"
            + "rpme.status,rpme.deskripsi,rpme.totalBelanja,rpme.isPosting) "
            + "FROM ReturPembelianMasterEntity rpme "
            + "WHERE rpme.idOutlet = ?1 AND lower(rpme.kodeReturPembelianMaster) LIKE %?2%")
    Page<ReturPembelianMasterListDTO> findAllReturPembelianByKey(Pageable page, UUID idOutlet, String keyword);

    Page<ReturPembelianMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<ReturPembelianMasterEntity> findByIdOutletAndId(UUID idOutlet,UUID id);

    Page<ReturPembelianMasterEntity> findByIdOutletAndKodeReturPembelianMasterContainingIgnoreCase(Pageable page, UUID idOutlet, String kodeReturPembelianMaster);

    @Query("SELECT rpm FROM ReturPembelianMasterEntity rpm WHERE rpm.idOutlet=?1")
    List<ReturPembelianMasterEntity> findReturPembelian(UUID idOutlet);

}
