/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.PembelianReportDTO;
import com.df.dolphinpos.dto.PenjualanReportDTO;
import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PembelianMasterEntity;
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

    Page<PembelianMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<PembelianMasterEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Page<PembelianMasterEntity> findByIdOutletAndKodePembelianMasterContainingIgnoreCase(Pageable page, UUID idOutlet, String kodePembelianMaster);

    @Query("SELECT "
            + "new com.df.dolphinpos.dto.PembelianReportDTO"
            + "(pm.tanggalPembelian, "
            + "pm.kodePembelianMaster, "
            + "pm.deskripsi, "
            + "pm.akunHolder.namaAkunHolder, "
            + "pm.kartuKontak.namaKontak,"
            + "pm.pengguna.username, "
            + "pm.disc, "
            + "pm.tax,"
            + "SUM(pd.hargaBeliBeli*pd.jumlahBeli-pd.disc)+"
            + "((pm.tax/100)*SUM(pd.hargaBeliBeli*pd.jumlahBeli-pd.disc)) )"
            + "FROM "
            + "PembelianMasterEntity pm "
            + "JOIN pm.pembelianDetail pd GROUP BY "
            + "pm.tanggalPembelian,"
            + "pm.kodePembelianMaster,"
            + "pm.deskripsi,"
            + "pm.akunHolder.namaAkunHolder,"
            + "pm.kartuKontak.namaKontak,"
            + "pm.pengguna.username,"
            + "pm.disc,"
            + "pm.tax")
    List<PembelianReportDTO> findPembelianReport();

    @Query("SELECT pm FROM PembelianMasterEntity pm")
    List<PembelianMasterEntity> findPembelian();

}
