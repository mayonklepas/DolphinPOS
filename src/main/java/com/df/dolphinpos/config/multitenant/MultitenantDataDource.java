/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.config.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @author mulyadi
 */
public class MultitenantDataDource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return MultitenantContext.getTenant();
    }

}
