package com.example.encryption;

import com.example.config.provider.CustomFileConfigProvider;
import org.apache.kafka.common.config.ConfigData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JasyptEncryptorTest {

    @Test
    public void encrypt() {
        JasyptEncryptor jasyptEncryptor = JasyptEncryptor.getInstance();
        String password = "something";
        System.out.println(jasyptEncryptor.encrypt(password));
    }

    @Test
    public void decrypt() {
        JasyptEncryptor jasyptEncryptor = JasyptEncryptor.getInstance();
        String encyptedPassword = "DYpR7S4R5ezuscqnk7rXBCf5wNeHytVz7KOUboQyNAE=";
        System.out.println(jasyptEncryptor.decrypt(encyptedPassword));
    }

    @Test
    public void decryptConfigData() {
        Map<String, String> data = new HashMap<>();
        data.put("username", "root");
        data.put("password", "ENC(DYpR7S4R5ezuscqnk7rXBCf5wNeHytVz7KOUboQyNAE=)");
        ConfigData configData = new ConfigData(data);

        CustomFileConfigProvider configProvider = new CustomFileConfigProvider();
        ConfigData decConfigData = configProvider.decrypt(configData);
        Map<String, String> decData = decConfigData.data();
        System.out.println(decData);
    }
}
