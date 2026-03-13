package app.forgeon.forgeon_api.config;

import app.forgeon.forgeon_api.security.AuthContext;
import app.forgeon.forgeon_api.security.AuthContextHolder;
import app.forgeon.forgeon_api.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Endpoints que NÃO precisam de JWT
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return HttpMethod.OPTIONS.matches(request.getMethod())
                || path.startsWith("/auth")
                || path.startsWith("/onboarding")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/error");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("===== JWT FILTER =====");
        System.out.println("PATH: " + request.getServletPath());

        String authHeader = request.getHeader("Authorization");
        System.out.println("AUTH HEADER: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
            {
              "error": "Authorization header ausente ou inválido"
            }
            """);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.validarToken(token);

            UUID usuarioPublicId = UUID.fromString(claims.getSubject());
            UUID empresaPublicId = UUID.fromString(
                    claims.get("empresa", String.class)
            );
            String role = claims.get("role", String.class);

            // Contexto customizado (seu)
            AuthContext authContext = new AuthContext(
                    usuarioPublicId,
                    empresaPublicId,
                    role
            );
            AuthContextHolder.set(authContext);

            // 🔥 AUTENTICAÇÃO DO SPRING (ISSO FALTAVA)
            JwtUserDetails userDetails = new JwtUserDetails();
            userDetails.setUserId(usuarioPublicId);
            userDetails.setEmpresaId(empresaPublicId);
// se não tiver email no token, pode remover
// userDetails.setEmail(claims.get("email", String.class));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, // ⭐ PRINCIPAL CORRETO
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);


            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("""
    {
      "error": "JWT inválido",
      "message": "%s"
    }
    """.formatted(e.getMessage()));
        }

    }
}
