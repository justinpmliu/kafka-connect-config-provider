package com.example.config.provider;

import com.example.encryption.JasyptEncryptor;
import org.apache.kafka.common.config.ConfigData;
import org.apache.kafka.common.config.provider.FileConfigProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomFileConfigProvider extends FileConfigProvider {

    public ConfigData get(String path) {
        ConfigData configData = super.get(path);
        return decrypt(configData);
    }

    public ConfigData get(String path, Set<String> keys) {
        ConfigData configData = super.get(path, keys);
        return decrypt(configData);
    }

    public ConfigData decrypt(ConfigData configData) {
        JasyptEncryptor encryptor = JasyptEncryptor.getInstance();

        Map<String, String> data = configData.data();
        Map<String, String> decData = new HashMap<>();

        for(Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value.startsWith("ENC(") && value.endsWith(")")) {
                String encStr = value.substring(4, value.length() - 1);
                decData.put(key, encryptor.decrypt(encStr));
            } else {
                decData.put(key, value);
            }
        }
        return new ConfigData(decData);
    }
}
