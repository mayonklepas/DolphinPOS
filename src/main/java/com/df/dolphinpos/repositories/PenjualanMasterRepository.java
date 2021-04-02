/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.ChartDto;
import com.df.dolphinpos.dto.PenjualanReportDTO;
import com.df.dolphinpos.dto.StrukDto;
import com.df.dolphinpos.entities.PenjualanMasterEntity;
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
public interface PenjualanMasterRepository extends PagingAndSortingRepository<PenjualanMasterEntity, UUID> {

    Page<PenjualanMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<PenjualanMasterEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Page<PenjualanMasterEntity> findByIdOutletAndKodePenjualanMasterContainingIgnoreCase(Pageable page, UUID idOutlet, String kodePenjualanMaster);

    @Query("SELECT pm FROM PenjualanMasterEntity pm WHERE pm.idOutlet=?1")
    List<PenjualanMasterEntity> findPenjualan(UUID idOutlet);

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
            + "(SELECT SUM((pd.hargaJualJual*pd.jumlahJual)-(pd.disc*pd.jumlahJual)) "
            + "FROM PenjualanDetailEntity pd "
            + "WHERE pd.idPenjualanMaster = pm.id)) "
            + "FROM "
            + "PenjualanMasterEntity pm "
            + "WHERE pm.idOutlet=?1 AND pm.tanggalPenjualan >= ?2 AND pm.tanggalPenjualan <= ?3 "
            + "ORDER BY pm.tanggalPenjualan DESC")
    List<PenjualanReportDTO> findPenjualanReport(UUID idOutlet, Date tanggalDari, Date tanggalHingga);

    @Query("SELECT pm FROM PenjualanMasterEntity pm WHERE pm.idOutlet=?1 AND pm.tanggalPenjualan >= ?2 and pm.tanggalPenjualan <= ?3 ORDER BY pm.tanggalPenjualan DESC")
    List<PenjualanMasterEntity> findPenjualanReportDetail(UUID idOutlet, Date tanggalDari, Date tanggalHingga);

    @Query("SELECT pm FROM PenjualanMasterEntity pm WHERE pm.id=?1 AND pm.idOutlet=?2 ORDER BY pm.tanggalPenjualan DESC")
    List<PenjualanMasterEntity> findPenjualanFakturById(UUID id, UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.StrukDto("
            + "be.namaBarang,"
            + "pd.disc,"
            + "pd.jumlahJual,"
            + "pd.hargaJualJual,"
            + "pm.disc,"
            + "pm.tax,"
            + "pm.kodePenjualanMaster,"
            + "pm.tanggalPenjualan,"
            + "pn.namaPengguna) "
            + "FROM PenjualanMasterEntity pm "
            + "JOIN pm.penjualanDetail pd "
            + "JOIN pd.barang be "
            + "JOIN pm.pengguna pn WHERE pm.id=?1 AND pm.idOutlet=?2")
    List<StrukDto> findStrukPenjualan(UUID id, UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.ChartDto(CAST(EXTRACT(DAY FROM pm.tanggalPenjualan) as string),"
            + "SUM((pd.jumlahJual*pd.hargaJualJual)-(pd.disc*pd.jumlahJual)))"
            + "FROM PenjualanMasterEntity pm "
            + "JOIN pm.penjualanDetail pd "
            + "WHERE pm.idOutlet=?1 AND EXTRACT(MONTH FROM pm.tanggalPenjualan)= EXTRACT(MONTH FROM CURRENT_DATE) "
            + "GROUP BY pm.tanggalPenjualan ORDER BY pm.tanggalPenjualan ASC")
    List<ChartDto> findChartPenjualan(UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.ChartDto('label',"
            + "SUM((pd.jumlahJual*pd.hargaJualJual)-(pd.disc*pd.jumlahJual))) "
            + "FROM PenjualanMasterEntity pm "
            + "JOIN pm.penjualanDetail pd "
            + "WHERE pm.idOutlet=?1 AND EXTRACT(MONTH FROM pm.tanggalPenjualan)= EXTRACT(MONTH FROM CURRENT_DATE)")
    ChartDto findTotalPenjualan(UUID idOutlet);
}
