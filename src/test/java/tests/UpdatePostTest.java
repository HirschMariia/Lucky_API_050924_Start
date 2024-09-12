package tests;

import dto.UpdatePostRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tests.BaseTest.*;

public class UpdatePostTest extends BaseTest{
// ЭТОТ ТЕСТ НЕ РАБОТАЕТ ПОЗЖЕ ПОЛАЖУ И ПОПРОБУЮ СДЕЛАТЬ ЛИСТ
    @Test
    public void updatePostSuccessfully() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");//получение токена

        UpdatePostRequest updateRequest = UpdatePostRequest.builder()
                .title("Dulce et Decorum Est/Blabla/030303")
                .description("Ut debitis iusto sint/Blabla/030303")
                .body("Hic sit nisi dolorem ipsum. Perferendis et labore ratione reiciendis iste molestiae accusantium. Quas facere quam. Iusto neque est sed eos qui non est/Blabla/030303")
                .draft(false)
                .build();

        String updateUrl = BASE_URI + "/api/posts/ad548295-7969-417d-971b-d260b42c2f8d";
        Response updateResponse = putRequest(updateUrl, updateRequest, 200, accessToken);


        //запрос PUT,десериализации(преобразования JSON-данных, полученных в HTTP-ответе) тела ответа в объект указанного типа
        UpdatePostRequest updatedPost = updateResponse.body().as(UpdatePostRequest.class);
        assertEquals(200, updateResponse.statusCode());
        assertAll(
                () -> assertEquals(updateRequest.getTitle(), updatedPost.getTitle()),
                () -> assertEquals(updateRequest.getDescription(), updatedPost.getDescription()),
                () -> assertEquals(updateRequest.getBody(), updatedPost.getBody()),
                () -> assertEquals(updateRequest.isDraft(), updatedPost.isDraft())
        );
    }
}
