/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.PenjualanReportDTO;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
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
public interface PenjualanMasterRepository extends PagingAndSortingRepository<PenjualanMasterEntity, UUID> {

    Page<PenjualanMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<PenjualanMasterEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Page<PenjualanMasterEntity> findByIdOutletAndKodePenjualanMasterContainingIgnoreCase(Pageable page, UUID idOutlet, String kodePenjualanMaster);

    @Query("SELECT "
            + "new com.df.dolphinpos.dto.PenjualanReportDTO"
            + "(pm.tanggalPenjualan, "
            + "pm.kodePenjualanMaster, "
            + "pm.deskripsi, "
            + "pm.akunHolder.namaAkunHolder, "
            + "pm.kartuKontak.namaKontak,"
            + "pm.pengguna.username, "
            + "pm.disc, "
            + "pm.tax,"
            + "SUM(pd.hargaJualJual*pd.jumlahJual-pd.disc)+"
            + "((pm.tax/100)*SUM(pd.hargaJualJual*pd.jumlahJual-pd.disc)) )"
            + "FROM "
            + "PenjualanMasterEntity pm "
            + "JOIN pm.penjualanDetail pd GROUP BY "
            + "pm.tanggalPenjualan,"
            + "pm.deskripsi,"
            + "pm.akunHolder.namaAkunHolder,"
            + "pm.kartuKontak.namaKontak,"
            + "pm.pengguna.username,"
            + "pm.disc,"
            + "pm.tax,"
            + "pm.kodePenjualanMaster")
    List<PenjualanReportDTO> findPenjualanReport();

    @Query("SELECT pm FROM PenjualanMasterEntity pm")
    List<PenjualanMasterEntity> findPenjualan();
}
