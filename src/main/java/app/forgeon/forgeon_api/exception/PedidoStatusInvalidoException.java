package app.forgeon.forgeon_api.exception;

import app.forgeon.forgeon_api.enums.StatusPedido;

public class PedidoStatusInvalidoException extends RuntimeException {

    public PedidoStatusInvalidoException(String acao, StatusPedido statusAtual) {
        super("Pedido nao pode ser " + acao + " quando status = " + statusAtual);
    }
}
