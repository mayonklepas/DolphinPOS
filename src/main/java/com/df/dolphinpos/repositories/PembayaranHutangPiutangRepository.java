/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;


import com.df.dolphinpos.entities.PembayaranHutangPiutangEntity;
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
public interface PembayaranHutangPiutangRepository extends PagingAndSortingRepository<PembayaranHutangPiutangEntity, UUID> {

    Page<PembayaranHutangPiutangEntity> findByIdHutangPiutangAndIdOutlet(Pageable page,UUID idHutanPiutang,UUID idOutlet);

    Page<PembayaranHutangPiutangEntity> findByIdHutangPiutangAndDeskripsiContainingIgnoreCase(Pageable page,UUID idHutanPiutang,String deskripsi);
    
    @Transactional
    @Modifying()
    @Query("DELETE FROM PembayaranHutangPiutangEntity php WHERE php.idHutangPiutang=?1")
    int deleteByIdHutangPiutang(UUID idHutangPiutang);
    

}
