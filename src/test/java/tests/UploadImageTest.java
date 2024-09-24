package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UploadImageTest extends BaseTest {
    @Test
    public void uploadImageSuccessfully() {
        //Bug, should be status code 200, with 201 passed
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");
        File imageFile = new File("C:\\Users\\User\\Pictures\\for project\\Screenshot_4.png");
        String uploadUrl = "/api/images";
        Response response = postImageUploadRequest(uploadUrl, imageFile, 201, accessToken);
        assertEquals(200, response.statusCode());
    }
}

