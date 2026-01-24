package app.forgeon.forgeon_api.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoVendaDTO {

    private Long produtoId;
    private String nome;
    private Long quantidadeVendida;
    private Double faturamento;

}
