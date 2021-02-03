/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.AkunHolderEntity;
import com.df.dolphinpos.entities.CatatanEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Minami
 */
public interface CatatanRepository extends PagingAndSortingRepository<CatatanEntity, UUID> {

    Page<CatatanEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<CatatanEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);
    
    Page<CatatanEntity> findByIdOutletAndDeskripsiContainingIgnoreCase(Pageable page,UUID idOutlet,String deskripsi);
}
