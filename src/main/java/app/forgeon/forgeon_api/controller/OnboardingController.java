package app.forgeon.forgeon_api.controller;

import app.forgeon.forgeon_api.dto.onboarding.OnboardingRequestDTO;
import app.forgeon.forgeon_api.service.OnboardingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onboarding")
@AllArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid OnboardingRequestDTO dto) {
        onboardingService.criarEmpresaEAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
