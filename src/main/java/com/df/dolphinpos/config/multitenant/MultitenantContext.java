/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.config.multitenant;

/**
 *
 * @author mulyadi
 */
public class MultitenantContext {
    
    private static final ThreadLocal<String> tenant=new ThreadLocal<>();
    
    public static String getTenant(){
        return tenant.get();
    }
    
    public static void setTenant(String currentTenant){
        tenant.set(currentTenant);
    }
    
    public static void clear(){
        tenant.set(null);
    }
    
}
