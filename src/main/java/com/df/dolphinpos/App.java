/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.df.dolphinpos;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 *
 * @author Minami
 */
@SpringBootApplication()
public class App {

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).web(WebApplicationType.SERVLET).run(args);
    }

}
