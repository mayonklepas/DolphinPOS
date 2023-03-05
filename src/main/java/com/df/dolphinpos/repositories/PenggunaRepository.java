/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PenggunaEntity;
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
public interface PenggunaRepository extends PagingAndSortingRepository<PenggunaEntity, UUID> {

    Page<PenggunaEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<PenggunaEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);
    
    PenggunaEntity findByUsername(String username);

    @Query("SELECT pe FROM PenggunaEntity pe WHERE pe.username=?1 AND pe.password=?2")
    PenggunaEntity findLogin(String username, String password);
}
