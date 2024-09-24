package tests;

import dto.UpdateUserPassRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UpdateUserPasswordTest extends BaseTest{
    @Test
    public void updateUserPasswordSuccessfully() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        UpdateUserPassRequest updatePassRequest = UpdateUserPassRequest.builder()
                .currentPassword("Blabla2024!!!")
                .newPassword("NewOne!!01")
                .confirmPassword("NewOne!!01")
                .build();
        String updatePasswordUrl = BASE_URI + "/api/user/password/update";
        Response updatePasswordResponse = putRequest(updatePasswordUrl, updatePassRequest, 200, accessToken);
        assertEquals(200, updatePasswordResponse.statusCode());
    }
    @Test
    public void updateUserPasswordWithIncorrectCurrentPassword() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        UpdateUserPassRequest updatePassRequest = UpdateUserPassRequest.builder()
                .currentPassword("NeBlabla2024!!")
                .newPassword("Blabla20!!!")
                .confirmPassword("Blabla20!!!")
                .build();
        String updatePasswordUrl = BASE_URI + "/api/user/password/update";
        Response updatePasswordResponse = putRequest(updatePasswordUrl, updatePassRequest, 400, accessToken);
        String errorMessage = updatePasswordResponse.body().jsonPath().getString("message");
        assertTrue(errorMessage.contains("Current password is incorrect"));
        String httpStatus = updatePasswordResponse.body().jsonPath().getString("httpStatus");
        assertEquals("BAD_REQUEST", httpStatus);
    }
    @Test
    public void updateUserPasswordWithShortNewPassword() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена
        UpdateUserPassRequest updatePassRequest = UpdateUserPassRequest.builder()
                .currentPassword("NewOne!!01")
                .newPassword("Bl2!")
                .confirmPassword("Bl2!")
                .build();

        String updatePasswordUrl = BASE_URI + "/api/user/password/update";
        Response updatePasswordResponse = putRequest(updatePasswordUrl, updatePassRequest, 400, accessToken);
        assertEquals(400, updatePasswordResponse.statusCode());
        String errorMessage = updatePasswordResponse.body().jsonPath().getString("newPassword[0]");
        assertTrue(errorMessage.contains("Password must contain at least 8 characters"));
    }
}
