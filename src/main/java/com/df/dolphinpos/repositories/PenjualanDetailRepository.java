/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.ChartDto;
import com.df.dolphinpos.dto.MarginPenjualanDTO;
import com.df.dolphinpos.dto.StrukDto;
import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PenjualanDetailEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Minami
 */
@Repository
public interface PenjualanDetailRepository extends JpaRepository<PenjualanDetailEntity, UUID> {

    List<PenjualanDetailEntity> findByIdPenjualanMaster(UUID idPenjualan);

    List<PenjualanDetailEntity> findByIdPenjualanMasterAndIdOutlet(UUID idPenjualan, UUID idOutlet);

    @Transactional
    @Modifying()
    @Query("DELETE FROM PenjualanDetailEntity pde WHERE pde.idPenjualanMaster=?1 AND pde.idOutlet = ?2")
    int deleteByIdPenjualanMasterAndIdOutlet(UUID idPenjualanMaster, UUID idOutlet);

    @Transactional
    @Modifying()
    @Query("DELETE FROM PenjualanDetailEntity pde WHERE pde.idPenjualanMaster=?1")
    int deleteByIdPenjualanMaster(UUID idPenjualanMaster);

    @Query("SELECT new com.df.dolphinpos.dto.ChartDto(be.namaBarang,SUM(pd.jumlahJual)) "
            + "FROM PenjualanDetailEntity pd JOIN pd.barang be "
            + "WHERE pd.idOutlet=?1 AND EXTRACT(MONTH FROM pd.dateCreated)= EXTRACT(MONTH FROM CURRENT_DATE) "
            + "GROUP BY be.namaBarang")
    List<ChartDto> getTopselling(UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.MarginPenjualanDTO("
            + "be.kodeBarang,"
            + "be.namaBarang,"
            + "be.satuanBarang,"
            + "be.hargaBeli,"
            + "SUM(pd.disc),"
            + "SUM((pd.hargaJualJual*pd.jumlahJual) - (pd.disc*pd.jumlahJual)),"
            + "SUM(pd.jumlahJual)) "
            + "FROM PenjualanDetailEntity pd JOIN pd.barang be "
            + "WHERE pd.idOutlet=?1 AND pd.tanggalTransaksi >= ?2 AND pd.tanggalTransaksi <=?3 "
            + "GROUP BY be.id")
    List<MarginPenjualanDTO> findMarginPenjualan(UUID idOutlet, Date tanggalDari, Date tanggalHingga);

    @Query("SELECT new com.df.dolphinpos.dto.MarginPenjualanDTO("
            + "be.kodeBarang,"
            + "be.namaBarang,"
            + "be.satuanBarang,"
            + "be.hargaBeli,"
            + "SUM(pd.disc),"
            + "SUM((pd.hargaJualJual*pd.jumlahJual) - (pd.disc*pd.jumlahJual)),"
            + "SUM(pd.jumlahJual)) "
            + "FROM PenjualanDetailEntity pd JOIN pd.barang be "
            + "WHERE pd.idOutlet=?1 AND be.kodeBarang=?2  AND pd.tanggalTransaksi >= ?3 AND pd.tanggalTransaksi <=?4 "
            + "GROUP BY be.id")
    Optional<MarginPenjualanDTO> findMarginPenjualanByKodeBarang(UUID idOutlet, String kodeBarang, Date tanggalDari, Date tanggalHingga);

}
