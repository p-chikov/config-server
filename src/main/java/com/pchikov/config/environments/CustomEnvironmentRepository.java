package com.pchikov.config.environments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.encryption.EncryptionController;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;

import java.util.Map;

@ConfigurationProperties("spring.cloud.config.server.custom")
public class CustomEnvironmentRepository implements EnvironmentRepository, Ordered {

    public static final String CIPHER_PREFIX = "{cipher}";

    @Autowired
    EncryptionController encryptionController;

    private int order = Ordered.LOWEST_PRECEDENCE;

    public CustomEnvironmentRepository() {
    }

    public CustomEnvironmentRepository(int order) {
        this.order = order;

    }

    final String CONFIG_CLIENT_APP_NAME = "config-client";

    @Override
    public Environment findOne(String application, String profile, String label) {

        String s = encryptionController.encrypt("testencryption123", MediaType.ALL);


        switch (application) {
            case CONFIG_CLIENT_APP_NAME:
                Environment environment = new Environment(application, profile);

                Map<String, String> properties = Map.of("prop1", cipherise(s), "prop2", "prop2 from server");
                environment.add(new PropertySource(CONFIG_CLIENT_APP_NAME + "-properties", properties));
                return environment;
        }

        return new Environment(application, profile);
    }


    private String cipherise(String value) {
        return new StringBuilder(CIPHER_PREFIX).append(value).toString();
    }


    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}