/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.JurnalUmumMasterDTO;
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
public interface JurnalUmumMasterRepository extends PagingAndSortingRepository<JurnalUmumMasterEntity, UUID> {

    @Query("SELECT new com.df.dolphinpos.dto.JurnalUmumMasterDTO(jume.id,jume.idOutlet,jume.tanggalJurnal,"
            + "jume.noRef,jume.tanggalRef,jume.isPosting,jume.tipeJurnal,SUM(jude.debit),SUM(jude.kredit)) "
            + "FROM JurnalUmumMasterEntity jume JOIN jume.jurnalUmumDetail jude "
            + "WHERE jume.idOutlet = ?1 GROUP BY jume.id")
    Page<JurnalUmumMasterDTO> findAllJurnal(Pageable page, UUID idOutlet);

    //Page<JurnalUmumMasterDTO> findAllJurnalByKey(Pageable page,UUID idOutlet,String keyword);
    Page<JurnalUmumMasterEntity> findByIdOutlet(Pageable page, UUID idOutlet);

    Optional<JurnalUmumMasterEntity> findByIdOutletAndId(UUID id, UUID idOutlet);

    Page<JurnalUmumMasterEntity> findByIdOutletAndNoRefContainingIgnoreCase(Pageable page, UUID idOutlet, String noRef);

    @Query("SELECT jum FROM JurnalUmumMasterEntity jum WHERE jum.idOutlet=?1")
    List<JurnalUmumMasterEntity> findJurnalUmum(UUID idOutlet);

}
