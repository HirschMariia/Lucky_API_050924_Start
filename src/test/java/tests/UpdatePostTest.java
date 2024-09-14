package tests;

import dto.UpdatePostRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpdatePostTest extends BaseTest {

    @Test
    public void updatePostSuccessfully() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");
        String userId = getUserId(accessToken);
        Response postsResponse = getRequest("/api/users/" + userId + "/posts?skip=0&limit=10", 200, accessToken);
        List<String> postIds = postsResponse.body().jsonPath().getList("id", String.class);
        assertFalse(postIds.isEmpty(), "The post list should not be empty");
        String postId = postIds.get(0);
        UpdatePostRequest updateRequest = UpdatePostRequest.builder()
                .title("Dulce et Decorum Est/Blabla/05")
                .description("jghjc kug")
                .body("gliu ';kl';/,m.lkbn")
                .draft(false)
                .build();
        Response updateResponse = putRequest("/api/posts/" + postId, updateRequest, 200, accessToken);
        assertEquals(200, updateResponse.statusCode());

    }
    @Test
    public void updatePostWithEmptyTitle() {
        String accessToken = loginAccessToken("hirsch.mariia@icloud.com", "NewOne!!01");
        String userId = getUserId(accessToken);
        Response postsResponse = getRequest("/api/users/" + userId + "/posts?skip=0&limit=10", 200, accessToken);
        List<String> postIds = postsResponse.body().jsonPath().getList("id", String.class);
        assertFalse(postIds.isEmpty());
        String postId = postIds.get(0);
        UpdatePostRequest updateRequest = UpdatePostRequest.builder()
                .title("")
                .description("This update should fail due to an empty title.")
                .body("Update with empty title.")
                .draft(false)
                .build();
        Response updateResponse = putRequest("/api/posts/" + postId, updateRequest, 400, accessToken);
        assertEquals(400, updateResponse.statusCode());
        List<String> errorMessages = updateResponse.body().jsonPath().getList("title");
        assertTrue(errorMessages.contains("Title can not be empty!") || errorMessages.contains("Title must contain from 1 to 40 characters"));
    }
}
