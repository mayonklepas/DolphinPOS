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

/**
 *
 * @author Minami
 */
public interface PenggunaRepository extends PagingAndSortingRepository<PenggunaEntity, UUID> {

    Page<PenggunaEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<PenggunaEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);
    
    PenggunaEntity findByUsername(String username);

    @Query("SELECT pe FROM PenggunaEntity pe WHERE pe.outlet.kodeOutlet=?1 AND pe.username=?2 AND pe.password=?3")
    PenggunaEntity findLogin(String kodeOutlet,String username, String password);
}
