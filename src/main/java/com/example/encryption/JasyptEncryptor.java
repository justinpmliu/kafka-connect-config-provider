package com.example.encryption;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

public class JasyptEncryptor {

    private static JasyptEncryptor INSTANCE;

    private PooledPBEStringEncryptor encryptor;

    private JasyptEncryptor() {
        encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
        encryptor.setPassword(System.getenv("JASYPT_ENCRYPTOR_PASSWORD"));
        encryptor.setIvGenerator(new RandomIvGenerator());
        encryptor.setKeyObtentionIterations(1000);
    }

    public static JasyptEncryptor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JasyptEncryptor();
        }
        return INSTANCE;
    }

    public String encrypt(String message) {
        return encryptor.encrypt(message);
    }

    public String decrypt(String message) {
        return encryptor.decrypt(message);
    }

}
