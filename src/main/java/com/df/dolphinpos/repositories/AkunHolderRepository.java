/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.AkunHolderEntity;
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
public interface AkunHolderRepository extends PagingAndSortingRepository<AkunHolderEntity, UUID> {

    Page<AkunHolderEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<AkunHolderEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Page<AkunHolderEntity> findByIdOutletAndNamaAkunHolderContainingIgnoreCase(Pageable page, UUID idOutlet, String namaAkunHolder);

 
}
