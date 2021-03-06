/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.service;

import com.df.dolphinpos.dto.AccountingServiceDTO;
import com.df.dolphinpos.dto.TestingDto;
import com.df.dolphinpos.entities.AkunKeuanganEntity;
import com.df.dolphinpos.entities.JurnalUmumDetailEntity;
import com.df.dolphinpos.entities.JurnalUmumMasterEntity;
import com.df.dolphinpos.repositories.AkunKeuanganRepository;
import com.df.dolphinpos.repositories.JurnalUmumDetailRepository;
import com.df.dolphinpos.repositories.JurnalUmumMasterRepository;
import com.df.dolphinpos.repositories.OutletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Minami
 */
@Service
public class AccountingService {

    @Autowired
    EntityManager enma;

    @Autowired
    JurnalUmumMasterRepository jurnalUmumMasterRepo;

    @Autowired
    JurnalUmumDetailRepository jurnalUmumDetailRepo;

    @Autowired
    AkunKeuanganRepository akunKeuanganRepo;

    @Transactional
    public void postingData(UUID outlet, UUID pengguna) throws JsonProcessingException, JSONException {

        /*String sqlgetSetting = "SELECT settings FROM outlet WHERE id=?";
        Query querySelectSetting = enma.createNativeQuery(sqlgetSetting);
        querySelectSetting.setParameter(1, outlet);
        Object resSetting = querySelectSetting.getSingleResult();
        String strSetting = String.valueOf(resSetting);
        JSONObject joSetting = new JSONObject(strSetting);
        String kodePenjualanDebit = joSetting.getJSONObject("akunPenjualanDebit").getString("id");
        String kodePenjualanKredit = joSetting.getJSONObject("akunPenjualanKredit").getString("id");
        String kodePembelianDebit = joSetting.getJSONObject("akunPembelianDebit").getString("id");
        String kodePembelianKredit = joSetting.getJSONObject("akunPembelianKredit").getString("id");
        String kodeReturPenjualanDebit = joSetting.getJSONObject("akunReturPenjualanDebit").getString("id");
        String kodeReturPenjualanKredit = joSetting.getJSONObject("akunReturPenjualanKredit").getString("id");
        String kodeReturPembelianDebit = joSetting.getJSONObject("akunReturPembelianDebit").getString("id");
        String kodeReturPembelianKredit = joSetting.getJSONObject("akunReturPembelianKredit").getString("id");*/
        String sqlGetakun
                = "SELECT CAST(id_akun_keuangan AS text) AS akun_debit,CAST(id_akun_keuangan_kredit AS text) AS akun_kredit,SUM(total_belanja) AS nominal,'penjualan' AS tipe FROM penjualan_master WHERE is_posting=0 GROUP BY id_akun_keuangan,id_akun_keuangan_kredit "
                + "union "
                + "SELECT CAST(id_akun_keuangan_debit AS text) AS akun_debit,CAST(id_akun_keuangan AS text) AS akun_kredit,SUM(total_belanja) AS nominal,'pembelian' AS tipe FROM pembelian_master WHERE is_posting=0 GROUP BY id_akun_keuangan_debit,id_akun_keuangan "
                + "union "
                + "SELECT CAST(id_akun_keuangan_debit AS text) AS akun_debit,CAST(id_akun_keuangan AS text) AS akun_kredit,SUM(total_belanja) AS nominal,'retur_penjualan' AS tipe FROM retur_penjualan_master WHERE is_posting=0 GROUP BY id_akun_keuangan_debit,id_akun_keuangan "
                + "union "
                + "SELECT CAST(id_akun_keuangan AS text) AS akun_debit,CAST(id_akun_keuangan_kredit AS text) AS akun_kredit,SUM(total_belanja) AS nominal,'retur_pembelian' AS tipe FROM retur_pembelian_master WHERE is_posting=0 GROUP BY id_akun_keuangan,id_akun_keuangan_kredit ORDER BY tipe";
        Query queryGetAkun = enma.createNativeQuery(sqlGetakun);
        List<Object[]> lsDataPosting = queryGetAkun.getResultList();

        if (lsDataPosting.size() > 0) {

            String currentDate = new SimpleDateFormat("yyyyMMddHms").format(new Date());

            JurnalUmumMasterEntity jurnalUmumMasterEntity = new JurnalUmumMasterEntity();
            jurnalUmumMasterEntity.setIdOutlet(outlet);
            jurnalUmumMasterEntity.setIdPengguna(pengguna);
            jurnalUmumMasterEntity.setTanggalJurnal(new java.sql.Date(System.currentTimeMillis()));
            jurnalUmumMasterEntity.setTanggalRef(new java.sql.Date(System.currentTimeMillis()));
            jurnalUmumMasterEntity.setNoRef(currentDate);
            jurnalUmumMasterEntity.setDeskripsi("Posting Data " + currentDate);
            UUID idJurnal = jurnalUmumMasterRepo.save(jurnalUmumMasterEntity).getId();

            //jurnal detail
            List<JurnalUmumDetailEntity> jurnalDetailList = new ArrayList<>();
            int indexUrutan = 1;
            for (int i = 0; i < lsDataPosting.size(); i++) {
                indexUrutan = indexUrutan + i;
                Object[] oDataPosting = lsDataPosting.get(i);
                String akunDebit = String.valueOf(oDataPosting[0]);
                String akunKredit = String.valueOf(oDataPosting[1]);
                double nominal = Double.parseDouble(String.valueOf(oDataPosting[2]));
                String tipeTrans = String.valueOf(oDataPosting[3]);

                JurnalUmumDetailEntity jurnalDetailDebit = new JurnalUmumDetailEntity();
                jurnalDetailDebit.setIdOutlet(outlet);
                jurnalDetailDebit.setIdPengguna(pengguna);
                jurnalDetailDebit.setTanggalJurnal(new java.sql.Date(System.currentTimeMillis()));
                jurnalDetailDebit.setIdJurnalMaster(idJurnal);
                jurnalDetailDebit.setIdAkunKeuangan(UUID.fromString(akunDebit));
                jurnalDetailDebit.setDeskripsi(tipeTrans + " " + currentDate);
                jurnalDetailDebit.setDebit(nominal);
                jurnalDetailDebit.setKredit(0);
                jurnalDetailDebit.setUrutan(indexUrutan);
                jurnalDetailList.add(jurnalDetailDebit);

                indexUrutan = indexUrutan + 1;
                JurnalUmumDetailEntity jurnalDetailKredit = new JurnalUmumDetailEntity();
                jurnalDetailKredit.setIdOutlet(outlet);
                jurnalDetailKredit.setIdPengguna(pengguna);
                jurnalDetailKredit.setTanggalJurnal(new java.sql.Date(System.currentTimeMillis()));
                jurnalDetailKredit.setIdJurnalMaster(idJurnal);
                jurnalDetailKredit.setIdAkunKeuangan(UUID.fromString(akunKredit));
                jurnalDetailKredit.setDeskripsi(tipeTrans + " " + currentDate);
                jurnalDetailKredit.setDebit(0);
                jurnalDetailKredit.setKredit(nominal);
                jurnalDetailKredit.setUrutan(indexUrutan);

                jurnalDetailList.add(jurnalDetailKredit);
            }
            jurnalUmumDetailRepo.saveAll(jurnalDetailList);

            String sqlupdatePosting = "UPDATE penjualan_master SET is_posting=1 WHERE is_posting=0;"
                    + "UPDATE pembelian_master SET is_posting=1 WHERE is_posting=0;"
                    + "UPDATE retur_penjualan_master SET is_posting=1 WHERE is_posting=0;"
                    + "UPDATE retur_pembelian_master SET is_posting=1 WHERE is_posting=0;";
            Query queryUpdatePosting = enma.createNativeQuery(sqlupdatePosting);
            queryUpdatePosting.executeUpdate();
        }

    }

    @Transactional
    public void Reindextahundata(UUID idOutlet, int tahun) throws JSONException, ParseException {
        String sqlgetSetting = "SELECT settings FROM outlet WHERE id=?";
        Query querySelectSetting = enma.createNativeQuery(sqlgetSetting);
        querySelectSetting.setParameter(1, idOutlet);
        Object resSetting = querySelectSetting.getSingleResult();
        String strSetting = String.valueOf(resSetting);
        JSONObject joSetting = new JSONObject(strSetting);
        String periodeAkuntansi = joSetting.getString("periodeAkuntansi");
        int bulanAwal = Integer.parseInt(periodeAkuntansi.split("-")[0]);
        int bulanAkhir = Integer.parseInt(periodeAkuntansi.split("-")[1]);

        int tahunAwal = Calendar.getInstance().get(Calendar.YEAR);
        int tahunAkhir = (bulanAwal > bulanAkhir) ? tahunAwal + 1 : tahunAwal;

        String bulanAwalString = (bulanAwal < 10) ? "0" + bulanAwal : String.valueOf(bulanAwal);
        String bulanAkhirString = (bulanAkhir < 10) ? "0" + bulanAkhir : String.valueOf(bulanAkhir);

        String tanggalAwal = tahunAwal + "-" + bulanAwalString + "-01";
        String tanggalAkhir = tahunAkhir + "-" + bulanAkhirString + "-31";

        Date dtAwal = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalAwal);
        Date dtAkhir = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalAkhir);

        akunKeuanganRepo.updateCurbalwithopbal(idOutlet);
        List<AkunKeuanganEntity> dataAkunKeuangan = akunKeuanganRepo.findByIdOutlet(idOutlet);
        int urutanGlobal = 1;
        for (AkunKeuanganEntity akunKeuanganEntity : dataAkunKeuangan) {
            UUID idAkunKeuangan = akunKeuanganEntity.getId();
            List<JurnalUmumDetailEntity> dataJurnalUmumDetail = jurnalUmumDetailRepo.getJurnalByAkunKeuangan(idOutlet, idAkunKeuangan, dtAwal, dtAkhir);
            for (JurnalUmumDetailEntity jurnalUmumDetailEntity : dataJurnalUmumDetail) {
                Query querygetcurbal = enma.createNativeQuery("SELECT current_balance FROM akun_keuangan WHERE id=?");
                querygetcurbal.setParameter(1, akunKeuanganEntity.getId());
                double curbal = Double.parseDouble(String.valueOf(querygetcurbal.getSingleResult()));
                double debit = Double.parseDouble(String.valueOf(jurnalUmumDetailEntity.getDebit()));
                double kredit = Double.parseDouble(String.valueOf(jurnalUmumDetailEntity.getKredit()));
                if (debit == 0) {
                    AkunKeuanganEntity setakun = akunKeuanganRepo.findById(idAkunKeuangan).get();
                    setakun.setCurrentBalance(curbal - kredit);
                    akunKeuanganRepo.save(setakun);
                    JurnalUmumDetailEntity setJurnal = jurnalUmumDetailRepo.findById(jurnalUmumDetailEntity.getId()).get();
                    setJurnal.setSaldo(curbal - kredit);
                    setJurnal.setUrutanGlobal(urutanGlobal);
                    jurnalUmumDetailRepo.save(setJurnal);
                } else {
                    AkunKeuanganEntity setakun = akunKeuanganRepo.findById(idAkunKeuangan).get();
                    setakun.setCurrentBalance(curbal + debit);
                    akunKeuanganRepo.save(setakun);
                    JurnalUmumDetailEntity setJurnal = jurnalUmumDetailRepo.findById(jurnalUmumDetailEntity.getId()).get();
                    setJurnal.setSaldo(curbal + debit);
                    setJurnal.setUrutanGlobal(urutanGlobal);
                    jurnalUmumDetailRepo.save(setJurnal);
                }
                urutanGlobal = urutanGlobal + 1;
            }

        }

    }

}
