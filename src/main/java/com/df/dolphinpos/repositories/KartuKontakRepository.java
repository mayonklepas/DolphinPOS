/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.AkunKeuanganEntity;
import com.df.dolphinpos.entities.KartuKontakEntity;
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
public interface KartuKontakRepository extends PagingAndSortingRepository<KartuKontakEntity, UUID> {

    Page<KartuKontakEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<KartuKontakEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);
    
    Page<KartuKontakEntity> findByIdOutletAndNamaKontakContainingIgnoreCase(Pageable page, UUID idOutlet,String namaKontak);
    
    Page<KartuKontakEntity> findByIdOutletAndTipeKontak(Pageable page, UUID idOutlet,int tipe);
    
    @Query("SELECT kk FROM KartuKontakEntity kk WHERE kk.idOutlet=?1 AND kk.tipeKontak=?2 ORDER BY kk.dateCreated DESC")
    List<KartuKontakEntity> findKontak(UUID idOutlet,int tipeKontak);
    
    @Query("SELECT kk FROM KartuKontakEntity kk WHERE kk.idOutlet=?1 AND (kk.tipeKontak=?2 OR kk.tipeKontak=2 )  ORDER BY kk.dateCreated DESC")
    Page<KartuKontakEntity> findKontakByTipe(Pageable page,UUID idOutlet,int tipeKontak);
    
    
}
