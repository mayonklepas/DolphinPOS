/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Minami
 */
@Controller
@RequestMapping("/")
public class PageController {
    
    String url="http://localhost:555/api";
    
    @GetMapping("index")
    public String index(){
        return "index";
    }
    
    @GetMapping("akunkeuangan")
    public String akunkeuangan(Model model){
        model.addAttribute("url", url);
        model.addAttribute("idOutlet", "b34e6e7b-6109-4bba-ad58-a603056b4552");
        model.addAttribute("idPengguna", "2981c3ad-f7c9-4b55-b469-abed360a8e01");
        return "akunkeuangan";
    }
    
}
