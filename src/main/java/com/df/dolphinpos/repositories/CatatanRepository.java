/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.ChartDto;
import com.df.dolphinpos.entities.AkunKeuanganEntity;
import com.df.dolphinpos.entities.CatatanEntity;
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
public interface CatatanRepository extends PagingAndSortingRepository<CatatanEntity, UUID> {

    Page<CatatanEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<CatatanEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Page<CatatanEntity> findByIdOutletAndDeskripsiContainingIgnoreCase(Pageable page, UUID idOutlet, String deskripsi);

    @Query("SELECT ce FROM CatatanEntity ce WHERE ce.idOutlet=?1 AND ce.tanggalCatatan >= ?2 AND ce.tanggalCatatan <= ?3 ORDER BY ce.tanggalCatatan DESC")
    List<CatatanEntity> findCatatan(UUID idOutlet,Date tanggalDari, Date tanggalHingga);
    
    @Query("SELECT ce FROM CatatanEntity ce WHERE ce.idOutlet=?1 AND ce.tipeCatatan = ?2 AND ce.tanggalCatatan >= ?3 AND ce.tanggalCatatan <= ?4 ORDER BY ce.tanggalCatatan DESC")
    List<CatatanEntity> findCatatanByTipe(UUID idOutlet,int tipe,Date tanggalDari, Date tanggalHingga);

    @Query("SELECT new com.df.dolphinpos.dto.ChartDto('',SUM(ce.jumlah)) "
            + "FROM CatatanEntity ce "
            + "WHERE ce.idOutlet=?1 AND "
            + "ce.tipeCatatan=0 AND "
            + "EXTRACT(MONTH FROM ce.tanggalCatatan)= EXTRACT(MONTH FROM CURRENT_DATE)")
    ChartDto findTotalPendapatanCatatan(UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.ChartDto('',SUM(ce.jumlah)) "
            + "FROM CatatanEntity ce "
            + "WHERE ce.idOutlet=?1 AND "
            + "ce.tipeCatatan=1 AND "
            + "EXTRACT(MONTH FROM ce.tanggalCatatan)= EXTRACT(MONTH FROM CURRENT_DATE)")
    ChartDto findTotalPengeluaranCatatan(UUID idOutlet);
}
