// Pacote
package peststore;

// Bibliotecas
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// Classe
public class Pet {
    // A linha abaixo é a declaração do Logger, que não estava presente no código original
    private static final Logger log = LoggerFactory.getLogger(Pet.class);

    // Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // URL correta

    // Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority = 1)
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
                .when()
                .post(uri)  // Ajustado para usar "uri" diretamente
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Thor"))
                .body("status", is("available"))
                .body("category.name", is("AX2345LORP96"));

    }

    // Consultar - Get
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "20302015";

        // Realizando a requisição GET e extraindo o valor de 'category.name'
        String token = given()
                .contentType("application/json")
                .log().all()
                .when()
                .get(uri + "/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Thor"))
                .body("category.name", is("AX2345LORP96"))
                .body("status", is("available"))
                .extract()
                .path("category.name");  // Aqui, extraímos o valor do campo 'category.name' e armazenamos em 'token'

        // Exibindo o valor extraído
        System.out.println("O token é " + token);
    }
}
