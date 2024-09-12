package tests;
import dto.UpdateUserInfoRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class UpdateUserInfoTest extends BaseTest {
    @Test
    public void updateUserInfoSuccessfully() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        String userId = getUserId(accessToken);//получение айдишки

        UpdateUserInfoRequest updateRequest = UpdateUserInfoRequest.builder()
                .name("Toma")
                .surname("Tomovna")
                .phone("+1234567890")
                .birthDate("1992-01-01T00:00:00.000Z")
                .gender("FEMALE")
                .build();

        String updateUrl = BASE_URI + "/api/users/" + userId;//тут ставили "id"
        Response updateResponse = putRequest(updateUrl, updateRequest, 200, accessToken);//"accessToken"
        UpdateUserInfoRequest updatedUserInfo = updateResponse.body().as(UpdateUserInfoRequest.class);

        assertAll(
                () -> assertEquals(updateRequest.getName(), updatedUserInfo.getName()),
                () -> assertEquals(updateRequest.getSurname(), updatedUserInfo.getSurname()),
                () -> assertEquals(updateRequest.getBirthDate(), updatedUserInfo.getBirthDate()),
                () -> assertEquals(updateRequest.getPhone(), updatedUserInfo.getPhone()),
                () -> assertEquals(updateRequest.getGender(), updatedUserInfo.getGender())
        );
    }


    @Test
    public void updateDateOfBirthday() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        String userId = getUserId(accessToken);//получение айдишки
        UpdateUserInfoRequest updateRequest = UpdateUserInfoRequest.builder()
                .birthDate("1892-01-01T00:00:00.000Z")
                .build();

        String updateUrl = BASE_URI + "/api/users/" + userId;
        Response updateResponse = putRequest(updateUrl, updateRequest, 400, accessToken);

        assertEquals(400, updateResponse.statusCode());
        String errorMessage = updateResponse.body().jsonPath().getString("birthDate");
        assertTrue(errorMessage.contains("Incorrect birthdate"));

    }
    @Test
    public void updateInfoWithInvalidPhoneFormat() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        String userId = getUserId(accessToken);//получение айдишки

        UpdateUserInfoRequest updateRequest = UpdateUserInfoRequest.builder()
                .phone("123blabla")
                .build();

        String updateUrl = BASE_URI + "/api/users/" + userId;
        Response updateResponse = putRequest(updateUrl, updateRequest, 400, accessToken);

        assertEquals(400, updateResponse.statusCode(), "Expected 400 Bad Request due to invalid phone format");

        List<String> errorMessages = updateResponse.body().jsonPath().getList("phone", String.class);
        assertAll(
                () -> assertTrue(errorMessages.contains("Phone must contain + and numbers")),
                () -> assertTrue(errorMessages.contains("Phone must contain from 10 to 12characters"))
        );

    }
    @Test
    public void updateInfoWithExcessivelyLongName() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        String userId = getUserId(accessToken);//получение айдишки

        UpdateUserInfoRequest updateRequest = UpdateUserInfoRequest.builder()
                .name("Toma".repeat(50))
                .surname("Tomovna")
                .build();

        String updateUrl = BASE_URI + "/api/users/" + userId;
        Response updateResponse = putRequest(updateUrl, updateRequest, 400, accessToken);

        assertEquals(400, updateResponse.statusCode());
        assertTrue(updateResponse.body().asString().contains("Name must contain from 3 to 20 character"));
    }
    @Test
    public void updateInfoWithInvalidGenderFormat() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        String userId = getUserId(accessToken);//получение айдишки

        UpdateUserInfoRequest updateRequest = UpdateUserInfoRequest.builder()
                .gender("Transgender")
                .build();

        String updateUrl = BASE_URI + "/api/users/" + userId;
        Response updateResponse = putRequest(updateUrl, updateRequest, 400, accessToken);

        assertEquals(400, updateResponse.statusCode());
        assertTrue(updateResponse.body().asString().contains("No enum constant org.example.chatty.entity.enums.Gender.Transgender"));

    }
    @Test
    public void emptyField() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        String userId = getUserId(accessToken);//получение айдишки

        UpdateUserInfoRequest updateRequest = UpdateUserInfoRequest.builder()
                .name("")
                .surname("")
                .phone("")
                .birthDate("")
                .gender("")
                .build();

        String updateUrl = BASE_URI + "/api/users/" + userId;
        Response updateResponse = putRequest(updateUrl, updateRequest, 400, accessToken);

        assertEquals(400, updateResponse.statusCode(), "Expected status code 400 due to validation errors.");//выплевывает список ошибок

        List<String> errorMessagesPhone = updateResponse.body().jsonPath().getList("phone", String.class);
        assertAll(
                () -> assertTrue(errorMessagesPhone.contains("Phone must contain + and numbers")),
                () -> assertTrue(errorMessagesPhone.contains("Phone must contain from 10 to 12characters"))
        );

        List<String> errorMessagesSurname = updateResponse.body().jsonPath().getList("surname", String.class);
        assertAll(
                () -> assertTrue(errorMessagesSurname.contains("Surname must contain from 3 to 20 characters")),
                () -> assertTrue(errorMessagesSurname.contains("must match \"^[A-Za-z-]+$\""))//  ?? "должно соответствовать \"^[A-Za-z-]+$\"" в свагере - проваливается
        );

        List<String> errorMessagesName = updateResponse.body().jsonPath().getList("name", String.class);
        assertAll(
                () -> assertTrue(errorMessagesName.contains("must match \"^[A-Za-z-]+$\"")), // ?? "должно соответствовать \"^[A-Za-z-]+$\""
                () -> assertTrue(errorMessagesName.contains("Name must contain from 3 to 20 characters"))
        );
    }
}