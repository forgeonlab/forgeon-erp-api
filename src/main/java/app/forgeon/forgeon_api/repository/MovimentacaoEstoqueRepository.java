package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.MovimentacaoEstoque;
import app.forgeon.forgeon_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimentacaoEstoqueRepository
        extends JpaRepository<MovimentacaoEstoque, UUID> {

    List<MovimentacaoEstoque> findAllByProdutoOrderByCriadoEmDesc(Produto produto);

}
