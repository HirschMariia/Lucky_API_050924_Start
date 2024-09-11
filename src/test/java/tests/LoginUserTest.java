package tests;

import dto.LoginUserRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.postRequest;

public class LoginUserTest {
    @Test
    public void successLoginUserRequiredFields() {
        String email = "hirsch.mariia@icloud.com";
        String password = "Blabla2024!";


        LoginUserRequest requestBody = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login", 200, requestBody);

        //LoginResponse loginResponse = response.body().jsonPath().getObject("", LoginResponse.class);
        //String loginResponseAccessToken = response.body().jsonPath().getString("accessToken");
        //assertFalse(loginResponseAccessToken.isEmpty());

        String loginResponseAccessToken = response.body().jsonPath().getString("accessToken");
//String loginResponseAccessToken = response.body().jsonPath().getObject("", LoginResponse.class); //забрать все
        assertFalse(loginResponseAccessToken.isEmpty());

    }
    @Test
    public void loginUserWithEmptyEmail() {
        String email = "";
        String password = "Blabla2024!";
        String errorMessage;

        LoginUserRequest requestBody = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login", 400, requestBody);


        List<String> errorMessages = response.body().jsonPath().getList("email", String.class);

        assertTrue(errorMessages.contains("Email cannot be empty"));
        assertTrue(errorMessages.contains("Invalid email format"));

    }
    @Test
    public void loginUserWithInvalidPassword() {
        String email = "hirsch.mariia@icloud.com";
        String password = "123";

        LoginUserRequest requestBody = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login", 400, requestBody);

        List<String> errorMessages = response.body().jsonPath().getList("password", String.class);

        assertTrue(errorMessages.contains("Password must contain letters and numbers"));
        assertTrue(errorMessages.contains("Password must contain at least 8 characters"));
    }
    @Test
    public void withoutPassword() {
        String email = "ghjk2@gmail.com";
        String password = "";


        LoginUserRequest requestBody = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login",  400,requestBody);


        List<String> errorMessages = response.body().jsonPath().getList("password", String.class);
        assertTrue(errorMessages.contains("Password cannot be empty"));


    }
    @Test
    public void InvalidPassWithOnlyCharacters() {
        String email = "hirsch.mariia@icloud.com";
        String password = "dfhgg";


        LoginUserRequest requestBody = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login",  400, requestBody);


        List<String> errorMessages = response.body().jsonPath().getList("password", String.class);
        assertTrue(errorMessages.contains("Password must contain at least 8 characters"));


    }


}