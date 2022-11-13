/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.service;

import com.df.dolphinpos.entities.PenggunaEntity;
import com.df.dolphinpos.repositories.PenggunaRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author mulyadi
 */
@Service
public class UserService implements UserDetailsService{

    @Autowired
    PenggunaRepository penggunarepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PenggunaEntity pengguna=penggunarepo.findByUsername(username);
        return new User(pengguna.getUsername(), pengguna.getPassword(), new ArrayList());
    }
    
    
    
}
