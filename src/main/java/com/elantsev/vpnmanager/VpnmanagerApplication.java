package com.elantsev.vpnmanager;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.processing.Filer;

@SpringBootApplication
public class VpnmanagerApplication {
   
    public static void main(String[] args) {
        SpringApplication.run(VpnmanagerApplication.class, args);

       /* BasicPasswordEncryptor bpEnc = new BasicPasswordEncryptor();
        System.out.println(bpEnc.encryptPassword("321"));*/
    }

    @Bean
    public BasicPasswordEncryptor basicPasswordEncryptor() {
        return new BasicPasswordEncryptor();
    }
}
