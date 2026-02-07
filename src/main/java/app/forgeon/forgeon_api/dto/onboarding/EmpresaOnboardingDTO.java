package app.forgeon.forgeon_api.dto.onboarding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmpresaOnboardingDTO(

        @NotBlank
        String nome,

        @NotBlank
        String cnpj,

        @NotBlank
        @Email
        String email,

        String telefone
) {}
