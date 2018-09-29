package com.mut.feeapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.mut.feeapi")
public class Main extends SpringBootServletInitializer {
    public static void main(String[] args) {

//         System.setProperty("server.tomcat.max-threads","0");
//         System.setProperty("server.connection-timeout","60000");
        new Main().configure(new SpringApplicationBuilder(Main.class)).run(args);

     }

}