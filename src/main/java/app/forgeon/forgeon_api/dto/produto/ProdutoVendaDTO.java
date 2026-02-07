package app.forgeon.forgeon_api.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoVendaDTO {

    private UUID produtoId;
    private String nome;
    private Long quantidadeVendida;
    private BigDecimal faturamento; // ✅ TEM que ser BigDecimal
}
