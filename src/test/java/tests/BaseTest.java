package tests;
import dto.LoginUserRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BaseTest {
    final static String BASE_URI = "http://chatty.telran-edu.de:8989";

    final static String APP_ID_VALUE = "65faa99005388ea765125437";// мой APP_ID ключ
    // спецификация, для всех запросов одна/ хотим передавать в наших запросах апп йди
    static RequestSpecification specification = new RequestSpecBuilder()//такой класс,позволяет общие настройки держать вместе
            // актуальны для всех запросов, везде добавлять эти настройки в
            // запросах  / повторяющиеся части
            .setBaseUri(BASE_URI) //
            .addHeader("app-id", APP_ID_VALUE)// в постман апп айди стоит в header(ключ, значение)
            .setContentType(ContentType.JSON)  // чтобы передавать в формате JSON
            .build();  // завершаем метод билд


    // метод, валиден для всех get запросов
    public static Response getRequest(String endpoint, Integer expectedStatusCode, String accessToken) {
        Response response = given()
                .spec(specification)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .log().all()
                .get(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;

    }

    //универсальный метод для всех POST
    public static Response postRequest(String endpoint, Integer expectedStatusCode, Object body) {
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .post(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    //универсальный метод для всех PUT
    public static Response putRequest(String endpoint, Object body, Integer expectedStatusCode, String accessToken) {
        Response response = given()
                .spec(specification)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .log().all()
                .put(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response deleteRequest(String endpoint, Integer expectedStatusCode, String accessToken) {
        Response response = given()
                .spec(specification)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .log().all()
                .delete(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    // Метод для получения accessToken после успешного входа
    public String loginAccessToken(String email, String password){
        LoginUserRequest requestBody = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login", 200, requestBody);

        String loginResponseAccessToken = response.body().jsonPath().getString("accessToken");
        assertFalse(loginResponseAccessToken.isEmpty());
        return loginResponseAccessToken;
    }
    public String getUserId(String accessToken){
        Response meResponse = getRequest("/api/me", 200, accessToken);
        String userId = meResponse.body().jsonPath().getString("id");
        assertFalse(userId.isEmpty());
        return userId;
    }

    // Метод для получения postId из списка постов пользователя НО ОНИ НЕ РАБОТАЮТ!
    //public static String getPostId(String accessToken) {
        // Выполняем GET запрос для получения всех постов пользователя
        //Response response = getRequest("/api/posts", 200, accessToken);
        // Извлечение списка постов
        //List<String> postIds = response.jsonPath().getList("id", String.class);
        // Проверка, что список постов не пустой и содержит хотя бы один postId
        //assertFalse(postIds.isEmpty(), "Post ID list should not be empty");
        // Возвращаем первый postId из списка
        //return postIds.get(0);

        /////////////////////////////////////////////////////////////////////////////////

       // Response response = getRequest("/api/posts", 200, accessToken);

        // Извлечение списка постов текущего пользователя
        //List<Map<String, Object>> posts = response.jsonPath().getList("");

        // Поиск первого поста, принадлежащего текущему пользователю
        //String currentUserId = getUserId(accessToken);
       // for (Map<String, Object> post : posts) {
         //   Map<String, Object> user = (Map<String, Object>) post.get("user");
          //  if (user.get("id").equals(currentUserId)) {
           //     return (String) post.get("id");
          //  }
       // }
       // return currentUserId;
   // }
}