package app.forgeon.forgeon_api.exception;

import java.util.UUID;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(UUID publicId) {
        super("Produto não encontrado: " + publicId);
    }
}
