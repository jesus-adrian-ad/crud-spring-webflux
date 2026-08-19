package asl.development.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    private static final String TITLE = "ASL CRUD Spring WebFlux API";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = """
            API REST reactiva para la administracion de productos.

            Construida con Spring WebFlux (endpoints funcionales) y Spring Data R2DBC sobre PostgreSQL,
            de forma que toda la cadena request -> servicio -> base de datos es no bloqueante.

            Todos los errores se serializan con el mismo contrato (`ErrorResponse`) gracias al
            manejador global de excepciones.""";

    @Value("${server.port:9091}")
    private int serverPort;

    @Bean
    public OpenAPI productsOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(TITLE)
                        .version(VERSION)
                        .description(DESCRIPTION)
                        .contact(new Contact()
                                .name("ASL Development")
                                .url("https://github.com/adrisoft-labs"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(new Server()
                        .url("http://localhost:" + serverPort)
                        .description("Entorno local")));
    }
}
