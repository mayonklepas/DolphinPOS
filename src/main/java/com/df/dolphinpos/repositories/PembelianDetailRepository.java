/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.PembelianDetailEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Minami
 */

@Repository
public interface PembelianDetailRepository extends JpaRepository<PembelianDetailEntity, UUID> {

    List<PembelianDetailEntity> findByIdPembelianMaster(UUID idPembelianMaster);

    @Transactional
    @Modifying()
    @Query("DELETE FROM PembelianDetailEntity pde WHERE pde.idPembelianMaster=?1")
    int deleteByIdPembelianMaster(UUID idPembelianMaster);
    
    
}
