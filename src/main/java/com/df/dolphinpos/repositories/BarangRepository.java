/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

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

/**
 *
 * @author Minami
 */
public interface BarangRepository extends PagingAndSortingRepository<BarangEntity, UUID> {

    Page<BarangEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<BarangEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);
    
    Optional<BarangEntity> findByIdOutletAndKodeBarang(UUID idOutlet,String kodeBarang);
    
    Page<BarangEntity> findByIdOutletAndNamaBarangContainingIgnoreCase(Pageable page,UUID idOutlet,String namaBarang);
    
    Page<BarangEntity> findByIdOutletAndTipeBarang(Pageable page,UUID idOutlet,int tipeBarang);
   
    @Query("SELECT be FROM BarangEntity be WHERE idOutlet=?1 AND lower(namaBarang) LIKE %?2% OR lower(kodeBarang) LIKE %?2%")
    Page<BarangEntity> findByKey(Pageable page,UUID idOutlet,String keyword);
  
    @Query("SELECT be FROM BarangEntity be WHERE be.idOutlet=?1 ORDER BY be.dateCreated DESC")
    List<BarangEntity> findBarang(UUID idOutlet);
    
    @Query("SELECT be FROM BarangEntity be WHERE be.idOutlet=?1 ORDER BY be.jumlahBarang ASC")
    List<BarangEntity> stokOverview(Pageable page,UUID idOulet);
    
    @Query("SELECT new com.df.dolphinpos.dto.BarangTotalDTO("
            + "SUM(be.hargaBeli*be.jumlahBarang),"
            + "SUM(be.hargaJual*be.jumlahBarang),"
            + "COUNT(be.jumlahBarang)) "
            + "FROM BarangEntity be WHERE be.idOutlet=?1")
    BarangTotalDTO getTotal(UUID idOutlet);
   
}
