package app.forgeon.forgeon_api.exception;

import java.util.UUID;

public class FilamentoNaoEncontradoException extends RuntimeException {

    public FilamentoNaoEncontradoException(UUID id) {
        super("Filamento nao encontrado: " + id);
    }
}
