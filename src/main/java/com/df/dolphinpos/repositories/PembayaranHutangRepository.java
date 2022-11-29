/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;


import com.df.dolphinpos.entities.PembayaranHutangEntity;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Minami
 */
public interface PembayaranHutangRepository extends PagingAndSortingRepository<PembayaranHutangEntity, UUID> {

    Page<PembayaranHutangEntity> findByIdOutletAndIdHutang(Pageable page,UUID idOutlet,UUID idHutan);

    Page<PembayaranHutangEntity> findByIdOutletAndIdHutangAndDeskripsiContainingIgnoreCase(Pageable page,UUID idOutlet,UUID idHutang,String deskripsi);
    
    @Transactional
    @Modifying()
    @Query("DELETE FROM PembayaranHutangEntity phe WHERE phe.idHutang=?1")
    int deleteByIdHutang(UUID idHutangPiutang);
    

}
