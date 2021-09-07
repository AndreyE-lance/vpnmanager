package com.elantsev.vpnmanager.encoder;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encoder implements PasswordEncoder {
    private final BasicPasswordEncryptor basicPasswordEncryptor;

    public Encoder(BasicPasswordEncryptor basicPasswordEncryptor) {
        this.basicPasswordEncryptor = basicPasswordEncryptor;
    }

    @Override
    public String encode(CharSequence charSequence) {
        return basicPasswordEncryptor.encryptPassword(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return basicPasswordEncryptor.checkPassword(charSequence.toString(),s);
    }
}
