package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPassRequest {
    private String id;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
