/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.service;

import com.df.dolphinpos.dto.ChartDto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Minami
 */
@Service
public class DashboardService {

    @Autowired
    EntityManager enma;

    public List<ChartDto> getChartPenjualan() {
        String sql = "SELECT "
                + "to_char(date_trunc('month', pm.tanggal_penjualan), 'Mon') AS label,"
                + "(SUM(pd.jumlah_jual*pd.harga_jual_jual-pd.disc)) as value "
                + "FROM penjualan_master pm inner join penjualan_detail pd on pm.id = pd.id_penjualan_master "
                + "GROUP BY date_trunc('month', pm.tanggal_penjualan)";
        Query query = enma.createNativeQuery(sql, ChartDto.class);
        List<ChartDto> data = query.getResultList();
        return data;
    }

}
