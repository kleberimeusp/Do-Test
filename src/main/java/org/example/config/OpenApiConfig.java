package org.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addRequestBodies("PedidoRequest",
                                new RequestBody()
                                        .content(new Content()
                                                .addMediaType(org.springframework.http.MediaType.APPLICATION_XML_VALUE, new MediaType().schema(new Schema<>().$ref("#/components/schemas/Pedido")))
                                                .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType().schema(new Schema<>().$ref("#/components/schemas/Pedido"))))));
    }

}