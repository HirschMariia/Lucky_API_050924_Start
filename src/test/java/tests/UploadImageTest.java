package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UploadImageTest extends BaseTest {
    @Test
    public void uploadImageSuccessfully() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");
        File imageFile = new File("C:\\Users\\User\\Pictures\\for project\\Screenshot_4.png");
        //if (!imageFile.exists() || !imageFile.isFile()) {
          //  throw new RuntimeException("Файл не найден или это не файл: " + imageFile.getAbsolutePath());
        //}
        String uploadUrl = "/api/images";
        Response response = postImageUploadRequest(uploadUrl, imageFile, 201, accessToken);
        assertEquals(201, response.statusCode(), "Ожидаемый статус код 201 для успешной загрузки изображения.");
    }
}

