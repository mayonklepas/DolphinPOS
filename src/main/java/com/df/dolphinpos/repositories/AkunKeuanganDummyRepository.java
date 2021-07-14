/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.repositories;

import com.df.dolphinpos.entities.AkunKeuanganDummyEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Minami
 */
public interface AkunKeuanganDummyRepository extends JpaRepository<AkunKeuanganDummyEntity, UUID> {


}
