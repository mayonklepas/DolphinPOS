/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.RakitanDetailEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Minami
 */


public interface RakitanDetailRepository extends JpaRepository<RakitanDetailEntity, UUID> {

    List<RakitanDetailEntity> findByIdRakitanMaster(UUID idRakitanMaster);

    @Transactional
    @Modifying()
    @Query("DELETE FROM RakitanDetailEntity rde WHERE rde.idRakitanMaster = ?1")
    int deleteByIdRakitanMaster(UUID idRakitanMaster);
    
    
}
