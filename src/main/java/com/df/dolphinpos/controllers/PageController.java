/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import com.df.dolphinpos.config.security.JwtUtil;
import com.df.dolphinpos.dto.AuthDTO;
import com.df.dolphinpos.dto.ResponseResult;
import com.df.dolphinpos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Minami
 */
@RestController
@RequestMapping("/")
public class PageController {

    String url = "http://localhost:8087/api";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("hello")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok("helo here");
    }

    @PostMapping("login")
    public ResponseResult authenticate(@RequestBody AuthDTO payload) {
        ResponseResult res = new ResponseResult();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
            UserDetails userDetails = userService.loadUserByUsername(payload.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            res.setCode(0);
            res.setStatus("success");
            res.setMessage("Authentikasi berhasil");
            res.setContent(jwt);
        } catch (Exception e) {
            res.setCode(1);
            res.setStatus("failed");
            res.setMessage("Authentikasi Gagal");
            res.setContent(e.getMessage());
        }

        return res;
    }

}
