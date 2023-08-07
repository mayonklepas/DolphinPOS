/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.config.security;

import com.df.dolphinpos.config.multitenant.MultitenantContext;
import com.df.dolphinpos.service.UserService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author mulyadi
 */
@Component
class RequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest hsr, HttpServletResponse hsr1, FilterChain fc) throws ServletException, IOException {
        String jwtToken = null;
        String userName = null;

        
        
        String tenantId = hsr.getHeader("tenantId");
        String clientUrl = hsr.getRequestURI();
        
        
        if(clientUrl.contains("struk") || clientUrl.contains("faktur")){
            tenantId = clientUrl.split("/")[6];
        }else if(clientUrl.contains("getimage")){
           tenantId = clientUrl.split("/")[4];
        }else if(clientUrl.contains("barcodebarang")){
            tenantId = clientUrl.split("/")[5];
        }
        
        
        System.out.println(tenantId);
        
        MultitenantContext.setTenant(tenantId);
        
        if (MultitenantContext.getTenant() == null) {
            throw new RuntimeException("Kode tidak ditemukan");
        } 
        

        String bearerToken = hsr.getHeader("Authorization");

        try {
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

                jwtToken = bearerToken.substring(7, bearerToken.length());
                userName = jwtUtil.extractUsername(jwtToken);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(userName);
                    if (jwtUtil.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(hsr));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RequestFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        fc.doFilter(hsr, hsr1);
    }

}
