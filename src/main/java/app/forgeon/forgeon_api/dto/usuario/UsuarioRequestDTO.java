package app.forgeon.forgeon_api.dto.usuario;

import app.forgeon.forgeon_api.enums.PapelUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {

    @NotNull(message = "O ID da empresa é obrigatório.")
    private Long empresaId;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 120)
    private String nome;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotNull(message = "O papel do usuário é obrigatório.")
    private PapelUsuario papel;

    private Boolean ativo = true;

    // Getters e Setters
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public PapelUsuario getPapel() { return papel; }
    public void setPapel(PapelUsuario papel) { this.papel = papel; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
