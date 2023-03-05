/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.JurnalUmumDTO;
import com.df.dolphinpos.dto.NeracaSaldoDTO;
import com.df.dolphinpos.entities.JurnalUmumDetailEntity;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Minami
 */
@Repository
public interface JurnalUmumDetailRepository extends JpaRepository<JurnalUmumDetailEntity, UUID> {

    List<JurnalUmumDetailEntity> findByIdJurnalMaster(UUID idJurnalMaster);

    @Transactional
    @Modifying()
    @Query("DELETE FROM JurnalUmumDetailEntity jud WHERE jud.idJurnalMaster=?1")
    int deleteByIdJurnalMaster(UUID idJurnalMaster);
    
    @Query("SELECT jude FROM JurnalUmumDetailEntity jude WHERE idOutlet=?1 AND idAkunKeuangan=?2 "
            + "AND EXTRACT(YEAR FROM tanggalJurnal)=?3 ORDER BY tanggalJurnal,dateCreated,urutan ASC")
    List<JurnalUmumDetailEntity> getJurnalByAkunKeuangan(UUID idOutlet,UUID idAkunKeuangan,int tahun);
    
    @Query("SELECT jude FROM JurnalUmumDetailEntity jude "
            + "WHERE idOutlet=?1 AND idAkunKeuangan=?2 "
            + "AND tanggalJurnal >= ?3 AND tanggalJurnal <= ?4  "
            + "ORDER BY tanggalJurnal,dateCreated,urutan ASC")
    List<JurnalUmumDetailEntity> getJurnalByAkunKeuangan(UUID idOutlet,UUID idAkunKeuangan,Date tanggalDari,Date tanggalHingga);
    
    /*@Query("SELECT com.df.dolphinpos.dto.JurnalUmumDTO("
            + "jue.tanggalJurnal,"
            + "ak.namaAkunKeuangan,"
            + "jue.deskripsi,"
            + "debit,"
            + "kredit,"
            + ") "
            + "FROM JurnalUmumDetailEntity jue JOIN akunKeuangan ak "
            + "WHERE jue.idOutlet=?1 "
            + "AND jue.tanggalJurnal >= ?2 "
            + "AND jue.tanggalJurnal <= ?3 "
            + "ORDER BY dateCreated DESC ")
    List<JurnalUmumDTO> getJurnalUmum();*/
    
    

}
