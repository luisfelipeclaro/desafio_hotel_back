package com.hotel.hotelchallengegradle.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    @Value("${hotel.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI init() {
        Server devServer = getNewSwaggerServerIO(devUrl, "Server URL in development environment.");
        return new OpenAPI().info(getInfo()).servers(Collections.singletonList(devServer));
    }

    private Server getNewSwaggerServerIO(String serverUrl, String description) {
        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription(description);
        return server;
    }

    private Contact getContactInfo() {
        Contact contact = new Contact();
        contact.setEmail("luisfelipe.claro@outlook.com");
        contact.setName("Luis Felipe Claro");
        return contact;
    }

    private Info getInfo() {
        Info info = new Info();
        info.setTitle("Hotel Challenge");
        info.setVersion("1.0.0");
        info.contact(getContactInfo());
        info.setDescription("This API exposes endpoints to manage this hotel challenge.");
        return info;
    }

}
