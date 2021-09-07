package com.elantsev.vpnmanager;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.nio.file.Path;

@SpringBootApplication
public class VpnmanagerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(VpnmanagerApplication.class, args);
        changeFileOwner("kirovav","159753");
    }

    @Bean
    public BasicPasswordEncryptor basicPasswordEncryptor() {
        return new BasicPasswordEncryptor();
    }

    public static boolean changeFileOwner(String toUser, String password){

        Process prc = null;
        try {
            prc = Runtime.getRuntime().exec("sudo -S chown "+toUser+" /etc/testvpn/vpn.conf");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(prc.getOutputStream()));
            bw.write(password+"\n");
            bw.flush();

            prc = Runtime.getRuntime().exec("ls -l /etc/testvpn/vpn.conf");
            BufferedReader bReader = new BufferedReader(new InputStreamReader(prc.getInputStream()));
            if(bReader.readLine().split(" ")[2].equals(toUser)) return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
