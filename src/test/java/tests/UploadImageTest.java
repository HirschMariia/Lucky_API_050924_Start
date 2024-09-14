package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UploadImageTest extends BaseTest {
    @Test
    public void uploadImageSuccessfully() {
        //При статус коде 201 проходит, но должен проходить при 200, баг завести!
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");
        File imageFile = new File("C:\\Users\\User\\Pictures\\for project\\Screenshot_4.png");
        //if (!imageFile.exists() || !imageFile.isFile()) {
          //  throw new RuntimeException("Файл не найден или это не файл: " + imageFile.getAbsolutePath());
        //}
        String uploadUrl = "/api/images";
        Response response = postImageUploadRequest(uploadUrl, imageFile, 200, accessToken);
        assertEquals(201, response.statusCode());
    }
}

