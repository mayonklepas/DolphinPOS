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
public interface PenjualanDetailRepository extends JpaRepository<PenjualanDetailEntity, UUID> {

    List<PenjualanDetailEntity> findByIdPenjualanMaster(UUID idPenjualan);

    @Transactional
    @Modifying()
    @Query("DELETE FROM PenjualanDetailEntity pde WHERE pde.idPenjualanMaster=?1")
    int deleteByIdPenjualanMaster(UUID idPenjualanMaster);

    @Query("SELECT new com.df.dolphinpos.dto.ChartDto(be.namaBarang,SUM(pd.jumlahJual)) "
            + "FROM PenjualanDetailEntity pd JOIN pd.barang be "
            + "WHERE pd.idOutlet=?1 AND EXTRACT(MONTH FROM pd.dateCreated)= EXTRACT(MONTH FROM CURRENT_DATE) "
            + "GROUP BY be.namaBarang")
    List<ChartDto> getTopselling(UUID idOutlet);

}
