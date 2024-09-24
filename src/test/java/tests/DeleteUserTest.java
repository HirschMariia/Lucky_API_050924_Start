package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteUserTest extends BaseTest {

    @Test
    public void deleteUserWithoutPermission() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");
        String userId = getUserId(accessToken);

        String deleteUrl = BASE_URI + "/api/users/" + userId;
        Response deleteResponse = deleteRequest(deleteUrl, 403, accessToken);

        assertEquals(403, deleteResponse.statusCode());
        assertEquals("You don't have permission to delete users",
                deleteResponse.body().jsonPath().getString("message"));
    }
}
