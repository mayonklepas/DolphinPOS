/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.dto.BarangTotalDTO;
import com.df.dolphinpos.dto.BukuBesarDTO;
import com.df.dolphinpos.entities.BarangDummyEntity;
import com.df.dolphinpos.entities.BarangEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Minami
 */
@Repository
public interface BarangDummyRepository extends JpaRepository<BarangDummyEntity, Long> {
   List<BarangDummyEntity> findByTipeToko(int tipeToko);
}
