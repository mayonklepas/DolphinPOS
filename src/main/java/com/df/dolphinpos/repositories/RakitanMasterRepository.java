/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.RakitanMasterListDTO;
import com.df.dolphinpos.entities.RakitanMasterEntity;
import java.sql.Date;
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
public interface RakitanMasterRepository extends PagingAndSortingRepository<RakitanMasterEntity, UUID> {

    @Query("SELECT new com.df.dolphinpos.dto.RakitanMasterListDTO("
            + "rme.id,rme.idOutlet,rme.tanggalPerakitan,rme.kodePerakitan,"
            + "rme.namaPerakitan,rme.kodeBarang,rme.namaBarang,rme.jumlahBarang) "
            + "FROM RakitanMasterEntity rme "
            + "WHERE rme.idOutlet = ?1 ")
    Page<RakitanMasterListDTO> findAllRakitan(Pageable page, UUID idOutlet);

   @Query("SELECT new com.df.dolphinpos.dto.RakitanMasterListDTO("
            + "rme.id,rme.idOutlet,rme.tanggalPerakitan,rme.kodePerakitan,"
            + "rme.namaPerakitan,rme.kodeBarang,rme.namaBarang,rme.jumlahBarang) "
            + "FROM RakitanMasterEntity rme "
            + "WHERE rme.idOutlet = ?1 AND (rme.kodeBarang=?2 OR rme.namaBarang=?2 ) ")
    Page<RakitanMasterListDTO> findAllPembelianByKey(Pageable page, UUID idOutlet, String keyword);

    Page<RakitanMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<RakitanMasterEntity> findByIdOutletAndId(UUID idOutlet, UUID id);
    
    Optional<RakitanMasterEntity> findByIdOutletAndKodeBarangOrderByDateCreatedDesc(UUID idOutlet, String kodeBarang);

    @Query("SELECT rm FROM RakitanMasterEntity rm WHERE rm.idOutlet=?1 AND rm.tanggalPerakitan >= ?2 and rm.tanggalPerakitan <= ?3 ORDER BY rm.dateCreated DESC")
    List<RakitanMasterEntity> findRakitanReportDetail(UUID idOutlet, Date tanggalDari, Date tanggalHingga);

}
