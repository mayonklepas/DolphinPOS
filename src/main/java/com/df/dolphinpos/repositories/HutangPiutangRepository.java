/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.HutangPiutangEntity;
import com.df.dolphinpos.entities.JurnalUmumMasterEntity;
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
public interface HutangPiutangRepository extends PagingAndSortingRepository<HutangPiutangEntity, UUID> {

    Page<HutangPiutangEntity> findByIdOutlet(Pageable page, UUID idOutlet);
    
    Page<HutangPiutangEntity> findByIdOutletAndTipe(Pageable page, UUID idOutlet, int tipe);

    Optional<HutangPiutangEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    @Query("SELECT hpe FROM HutangPiutangEntity hpe WHERE idOutlet=?2 AND tipe=?3 AND deskripsi=?4")
    Page<HutangPiutangEntity> findBySearch(Pageable page, UUID idOutlet,int tipe,String deskripsi);

    

}
