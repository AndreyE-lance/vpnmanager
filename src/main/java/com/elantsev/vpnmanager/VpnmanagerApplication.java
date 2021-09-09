package com.elantsev.vpnmanager;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;


@SpringBootApplication
public class VpnmanagerApplication {

    public static void main(String[] args) throws IOException {
        var context = SpringApplication.run(VpnmanagerApplication.class, args);

    }

    @Bean
    public BasicPasswordEncryptor basicPasswordEncryptor() {
        return new BasicPasswordEncryptor();
    }

}
