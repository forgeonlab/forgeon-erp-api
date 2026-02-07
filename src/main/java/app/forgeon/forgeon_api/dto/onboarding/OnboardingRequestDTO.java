package app.forgeon.forgeon_api.dto.onboarding;

import jakarta.validation.Valid;

public record OnboardingRequestDTO(
        @Valid EmpresaOnboardingDTO empresa,
        @Valid UsuarioOnboardingDTO usuario
) {}
