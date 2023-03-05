/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.HutangEntity;
import com.df.dolphinpos.entities.PiutangEntity;
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
public interface PiutangRepository extends PagingAndSortingRepository<PiutangEntity, UUID> {

    Page<PiutangEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<PiutangEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    @Query("SELECT pe FROM PiutangEntity pe WHERE idOutlet=?1 AND deskripsi LIKE %?2% ")
    Page<PiutangEntity> findBySearch(Pageable page, UUID idOutlet,String deskripsi);

    

}
