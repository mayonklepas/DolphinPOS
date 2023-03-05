/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.BarangDetailPenjualanPerakitanDTO;
import com.df.dolphinpos.dto.BarangTotalDTO;
import com.df.dolphinpos.dto.BukuBesarDTO;
import com.df.dolphinpos.entities.BarangEntity;
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
public interface BarangRepository extends PagingAndSortingRepository<BarangEntity, UUID> {

    Page<BarangEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Page<BarangEntity> findByIdOutletAndTipeBarang(Pageable page, UUID idOutlet, int tipeBarang);

    Optional<BarangEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Optional<BarangEntity> findByIdOutletAndKodeBarang(UUID idOutlet, String kodeBarang);

    Page<BarangEntity> findByIdOutletAndNamaBarangContainingIgnoreCase(Pageable page, UUID idOutlet, String namaBarang);

    Page<BarangEntity> findByIdOutletAndTipeBarangAndNamaBarangContainingIgnoreCase(Pageable page, UUID idOutlet, int tipeBarang, String namaBarang);

    @Query("SELECT be FROM BarangEntity be WHERE idOutlet=?1 AND lower(namaBarang) LIKE %?2% OR lower(kodeBarang) LIKE %?2%")
    Page<BarangEntity> findByKey(Pageable page, UUID idOutlet, String keyword);

    @Query("SELECT be FROM BarangEntity be WHERE idOutlet=?1 AND tipeBarang = ?2 AND (lower(namaBarang) LIKE %?3% OR lower(kodeBarang) LIKE %?3% OR lower(keterangan) LIKE %?3%)")
    Page<BarangEntity> findByAll(Pageable page, UUID idOutlet, int tipe, String keyword);

    @Query("SELECT be FROM BarangEntity be WHERE idOutlet=?1 AND (lower(namaBarang) LIKE %?2% OR lower(kodeBarang) LIKE %?2% OR lower(keterangan) LIKE %?2%) ")
    Page<BarangEntity> findByAll(Pageable page, UUID idOutlet, String keyword);

    @Query("SELECT be FROM BarangEntity be WHERE be.idOutlet=?1 ORDER BY be.dateCreated DESC")
    List<BarangEntity> findBarang(UUID idOutlet);

    @Query("SELECT be FROM BarangEntity be WHERE be.idOutlet=?1 AND be.tipeBarang=?2 ORDER BY be.dateCreated DESC")
    List<BarangEntity> findBarangByTipe(UUID idOutlet, int tipe);

    @Query("SELECT be FROM BarangEntity be WHERE be.idOutlet=?1 AND lower(be.kodeBarang) = ?2")
    List<BarangEntity> findBarangByKode(UUID idOutlet, String kodeBarang);

    @Query("SELECT be FROM BarangEntity be WHERE be.idOutlet=?1 ORDER BY be.jumlahBarang ASC")
    List<BarangEntity> stokOverview(Pageable page, UUID idOulet);

    @Query("SELECT new com.df.dolphinpos.dto.BarangTotalDTO("
            + "COALESCE(SUM(be.hargaBeli*be.jumlahBarang),0.0),"
            + "COALESCE(SUM(be.hargaJual*be.jumlahBarang),0.0),"
            + "COALESCE(COUNT(be.jumlahBarang),0)) "
            + "FROM BarangEntity be WHERE be.idOutlet=?1")
    BarangTotalDTO getTotal(UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.BarangTotalDTO("
            + "COALESCE(SUM(be.hargaBeli*be.jumlahBarang),0.0),"
            + "COALESCE(SUM(be.hargaJual*be.jumlahBarang),0.0),"
            + "COALESCE(COUNT(be.jumlahBarang),0)) "
            + "FROM BarangEntity be WHERE be.idOutlet=?1 and be.tipeBarang=?2")
    BarangTotalDTO getTotalBytipe(UUID idOutlet, int tipe);

    boolean existsByIdAndIdOutlet(UUID id, UUID idOutlet);

    boolean existsByIdOutletAndKodeBarang(UUID idOutlet, String kodeBarang);

}
