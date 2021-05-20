/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.ChartDto;
import com.df.dolphinpos.dto.StrukDto;
import com.df.dolphinpos.entities.PembelianDetailEntity;
import com.df.dolphinpos.entities.PenjualanDetailEntity;
import com.df.dolphinpos.entities.ReturPenjualanDetailEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Minami
 */
public interface ReturPenjualanDetailRepository extends JpaRepository<ReturPenjualanDetailEntity, UUID> {

    List<ReturPenjualanDetailEntity> findByIdReturPenjualanMaster(UUID idReturPenjualan);

    @Transactional
    @Modifying()
    @Query("DELETE FROM ReturPenjualanDetailEntity rpde WHERE rpde.idReturPenjualanMaster=?1")
    int deleteByIdReturPenjualanMaster(UUID idReturPenjualanMaster);


}
