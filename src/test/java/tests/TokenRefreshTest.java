package tests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class TokenRefreshTest {
    @Test
    public void testExpiredTokenUnauthorized() {
        String expiredAccessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhMjQ3NWVhNy03ZDM5LTQ0NmMtOTBiMS1jN2ExNTE0YWUwNGQiLCJyb2xlIjoiVVNFUiIsImV4cCI6MTcyNTkwMTI2MX0.Z7fXDIP0swhtMpVGmlgNCiPg_nkBmWakz2yqPeLUeLk";
        Response expiredTokenResponse = putRequest("/api/users/a2475ea7-7d39-446c-90b1-c7a1514ae04d", "{}", 401, expiredAccessToken);
        assertEquals(401, expiredTokenResponse.statusCode());
        assertTrue(expiredTokenResponse.body().jsonPath().getString("message").contains("Unauthorized"));
    }
}
