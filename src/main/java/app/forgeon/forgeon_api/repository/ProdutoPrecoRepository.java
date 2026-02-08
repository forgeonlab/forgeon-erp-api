package app.forgeon.forgeon_api.repository;

import app.forgeon.forgeon_api.model.ProdutoPreco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoPrecoRepository extends JpaRepository<ProdutoPreco, UUID> {
}
