package app.forgeon.forgeon_api.config;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class JwtUserDetails {

    private UUID userId;
    private UUID empresaId;
    private String email;
}
