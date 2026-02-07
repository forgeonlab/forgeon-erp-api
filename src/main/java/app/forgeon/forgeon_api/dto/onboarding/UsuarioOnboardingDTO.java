package app.forgeon.forgeon_api.dto.onboarding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioOnboardingDTO(

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha
) {}
