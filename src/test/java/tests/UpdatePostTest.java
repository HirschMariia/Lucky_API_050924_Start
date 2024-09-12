package tests;

import dto.UpdatePostRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UpdatePostTest extends BaseTest {

    @Test
    public void updatePostSuccessfully() {

        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");
        List<Map<String, Object>> posts = getAllPosts(accessToken);
        assertFalse(posts.isEmpty());

        String postId = (String) posts.get(0).get("id");
        Map<String, Object> post = getPostById(postId, accessToken);

        UpdatePostRequest updateRequest = UpdatePostRequest.builder()
                .title("Dulce et Decorum Est/Blabla/05")
                .description("xtjxfykxgukcguujlvuhj xtkylcguliyihj.,nnhsgearysfjhfkgj")
                .body("srfjtxyfkcyuyvlbjk,n zrtkykcghcfrsertrytuyiuilhkjhvgcfdsawdert57uyiuok;lk,nmjbnhbgfdsz")
                .draft(false)
                .build();
        String updateUrl = "/api/posts/" + postId;
        Response updateResponse = putRequest(updateUrl, updateRequest, 200, accessToken);
        assertEquals(200, updateResponse.statusCode(), "Обновление поста не удалось!");

        // Десериализация тела ответа в объект `UpdatePostRequest`
        UpdatePostRequest updatedPost = updateResponse.body().as(UpdatePostRequest.class);


        assertAll(
                () -> assertEquals(updateRequest.getTitle(), updatedPost.getTitle()),
                () -> assertEquals(updateRequest.getDescription(), updatedPost.getDescription()),
                () -> assertEquals(updateRequest.getBody(), updatedPost.getBody()),
                () -> assertEquals(updateRequest.isDraft(), updatedPost.isDraft())
        );
    }

}
