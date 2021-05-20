/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.KoreksiStokEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Minami
 */
public interface KoreksiStokRepository extends PagingAndSortingRepository<KoreksiStokEntity, UUID> {

    Page<KoreksiStokEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<KoreksiStokEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Page<KoreksiStokEntity> findByIdOutletAndDeskripsiContainingIgnoreCase(Pageable page, UUID idOutlet, String deskripsi);
}
