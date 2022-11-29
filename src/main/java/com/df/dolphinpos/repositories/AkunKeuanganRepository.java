/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.AkunKeuanganEntity;
import java.util.List;
import java.util.Optional;
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
public interface AkunKeuanganRepository extends PagingAndSortingRepository<AkunKeuanganEntity, UUID> {

    Page<AkunKeuanganEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    List<AkunKeuanganEntity> findByIdOutlet(UUID idOutlet);

    Optional<AkunKeuanganEntity> findByIdAndIdOutlet(UUID id, UUID idOutlet);

    Optional<AkunKeuanganEntity> findByKodeAkunKeuanganAndIdOutlet(String kodeAkunKeuangan, UUID idOutlet);

    @Query("SELECT ake FROM AkunKeuanganEntity ake WHERE idOutlet=?1 AND tipeAkun <> 3")
    List<AkunKeuanganEntity> findKasbank(UUID idOutlet);

    Page<AkunKeuanganEntity> findByIdOutletAndNamaAkunKeuanganContainingIgnoreCase(Pageable page, UUID idOutlet, String namaAkunKeuangan);

    @Transactional
    @Modifying()
    @Query("UPDATE AkunKeuanganEntity SET currentBalance=openingBalance WHERE idOutlet=?1")
    int updateCurbalwithopbal(UUID idOutlet);

    @Query("SELECT ake FROM AkunKeuanganEntity ake WHERE idOutlet=?1 ORDER BY kodeAkunKeuangan ASC")
    List<AkunKeuanganEntity> getDataAkunKeuangan(UUID idOutlet);

    @Query("SELECT ake FROM AkunKeuanganEntity ake WHERE ake.idOutlet=?1 AND ake.id=?2 ORDER BY kodeAkunKeuangan ASC")
    List<AkunKeuanganEntity> getDataAkunKeuanganById(UUID idOutlet, UUID id);

}
