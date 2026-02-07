package app.forgeon.forgeon_api.model.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshTokenRequest {
    private String refreshToken;
}

