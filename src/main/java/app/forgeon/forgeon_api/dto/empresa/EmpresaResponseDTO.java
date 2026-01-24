package app.forgeon.forgeon_api.dto.empresa;

import java.time.LocalDateTime;

public class EmpresaResponseDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
    private Boolean ativa;
    private LocalDateTime dataCriacao;

    // Construtor prático
    public EmpresaResponseDTO(Long id, String nome, String cnpj, String email, String telefone, Boolean ativa, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.ativa = ativa;
        this.dataCriacao = dataCriacao;
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCnpj() { return cnpj; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public Boolean getAtiva() { return ativa; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
