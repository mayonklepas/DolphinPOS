/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.RacikanMasterDto;
import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.RacikanEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Minami
 */
@Repository
public interface RacikanRepository extends PagingAndSortingRepository<RacikanEntity, UUID> {

    Page<RacikanEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<RacikanEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    @Query(value="SELECT new com.df.dolphinpos.dto.RacikanMasterDto(re.kodeResep,re.namaResep,MAX(re.dateCreated)) FROM RacikanEntity re WHERE re.idOutlet=?1 GROUP BY re.kodeResep,re.namaResep,re.dateCreated")
    Page<RacikanMasterDto> findByIdOutletGroupByKodeResep(Pageable page,UUID idOutlet);

    @Query("SELECT new com.df.dolphinpos.dto.RacikanMasterDto(re.kodeResep,re.namaResep,MAX(re.dateCreated)) FROM RacikanEntity re WHERE re.idOutlet=?1 AND lower(re.namaResep) LIKE %?2% GROUP BY re.kodeResep,re.namaResep,re.dateCreated")
    Page<RacikanMasterDto> findByIdOutletGroupByKodeResepAndKey(Pageable page,UUID idOutlet, String namaResep);

    List<RacikanEntity> findByIdOutletAndKodeResep(UUID idOutlet,String kodeResep);

    @Transactional
    @Modifying
    @Query("DELETE FROM RacikanEntity re WHERE re.idOutlet=?1 AND re.kodeResep=?2")
    int deleteBykodeResep(UUID idOutlet,String kodeResep);
}
 