package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreatePostByUserRequest {
   String title;
   String description;
   String body;
   String imageUrl;
   private boolean draft;
}
