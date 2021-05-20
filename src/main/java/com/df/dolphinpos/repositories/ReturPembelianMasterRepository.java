/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.ReturPembelianMasterEntity;
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
public interface ReturPembelianMasterRepository extends PagingAndSortingRepository<ReturPembelianMasterEntity, UUID> {

    Page<ReturPembelianMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<ReturPembelianMasterEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Page<ReturPembelianMasterEntity> findByIdOutletAndKodeReturPembelianMasterContainingIgnoreCase(Pageable page, UUID idOutlet, String kodeReturPembelianMaster);

    @Query("SELECT rpm FROM ReturPembelianMasterEntity rpm WHERE rpm.idOutlet=?1")
    List<ReturPembelianMasterEntity> findReturPembelian(UUID idOutlet);

   
    

}
