package tests;

import dto.CreatePostByUserRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePostByUserTest extends BaseTest {
    @Test
    public void createPostSuccessfully() {
//с 200 валится , надо завести БАГ
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");

        CreatePostByUserRequest createRequest = CreatePostByUserRequest.builder()
                .title("Hi435")
                .description("WOW22")
                .body("LAAA52")
                .imageUrl("string")
                .draft(false)
                .build();
        String createPostUrl = "/api/posts";
        Response createResponse = postRequestForCreatePost(createPostUrl, 201, createRequest, accessToken);
        assertEquals(201, createResponse.statusCode());
        String userId = getUserId(accessToken);
        Response postsResponse = getRequest("/api/users/" + userId + "/posts?skip=0&limit=10", 200, accessToken);
        List<String> postIds = postsResponse.body().jsonPath().getList("id", String.class);
        assertFalse(postIds.isEmpty());
        assertTrue(postIds.contains(createResponse.body().jsonPath().getString("id")));
    }
}
