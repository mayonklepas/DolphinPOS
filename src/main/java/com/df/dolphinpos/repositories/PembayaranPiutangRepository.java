/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;


import com.df.dolphinpos.entities.PembayaranPiutangEntity;
import com.df.dolphinpos.entities.PembayaranPiutangEntity;
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
public interface PembayaranPiutangRepository extends PagingAndSortingRepository<PembayaranPiutangEntity, UUID> {

    Page<PembayaranPiutangEntity> findByIdOutletAndIdPiutang(Pageable page,UUID idOutlet,UUID idPiutang);

    Page<PembayaranPiutangEntity> findByIdOutletAndIdPiutangAndDeskripsiContainingIgnoreCase(Pageable page,UUID idOutlet,UUID idPiutang,String deskripsi);
    
    @Transactional
    @Modifying()
    @Query("DELETE FROM PembayaranPiutangEntity ppe WHERE ppe.idPiutang=?1")
    int deleteByIdPiutang(UUID idPiutangPiutang);
    

}
