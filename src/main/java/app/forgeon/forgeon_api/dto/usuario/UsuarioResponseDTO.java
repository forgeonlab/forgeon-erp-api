package app.forgeon.forgeon_api.dto.usuario;

import app.forgeon.forgeon_api.enums.PapelUsuario;

import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Long id;
    private Long empresaId;
    private String nome;
    private String email;
    private PapelUsuario papel;
    private Boolean ativo;
    private LocalDateTime dataCriacao;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public PapelUsuario getPapel() { return papel; }
    public void setPapel(PapelUsuario papel) { this.papel = papel; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
