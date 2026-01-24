package app.forgeon.forgeon_api.dto.onboarding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OnboardingRequestDTO(

        @NotBlank
        String nomeEmpresa,

        @NotBlank
        String cnpj,

        @NotBlank
        @Email
        String emailEmpresa,

        @NotBlank
        String telefoneEmpresa,

        @NotBlank
        String nomeAdmin,

        @NotBlank
        @Email
        String emailAdmin,

        @NotBlank
        String senhaAdmin
) {}
