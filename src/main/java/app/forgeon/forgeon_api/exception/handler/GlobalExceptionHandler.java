package app.forgeon.forgeon_api.exception.handler;

import app.forgeon.forgeon_api.dto.error.ApiErrorDTO;
import app.forgeon.forgeon_api.exception.FilamentoNaoEncontradoException;
import app.forgeon.forgeon_api.exception.PedidoStatusInvalidoException;
import app.forgeon.forgeon_api.exception.ProdutoNaoEncontradoException;
import app.forgeon.forgeon_api.exception.SkuDuplicadoException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SkuDuplicadoException.class)
    public ResponseEntity<ApiErrorDTO> handleSkuDuplicado(SkuDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorDTO(
                        HttpStatus.CONFLICT.value(),
                        "SKU_DUPLICADO",
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ApiErrorDTO> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "PRODUTO_NAO_ENCONTRADO",
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(FilamentoNaoEncontradoException.class)
    public ResponseEntity<ApiErrorDTO> handleFilamentoNaoEncontrado(FilamentoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "FILAMENTO_NAO_ENCONTRADO",
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(PedidoStatusInvalidoException.class)
    public ResponseEntity<ApiErrorDTO> handlePedidoStatusInvalido(PedidoStatusInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "STATUS_PEDIDO_INVALIDO",
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "VALIDACAO_INVALIDA",
                        mensagem,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorDTO> handleMetodoNaoSuportado(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiErrorDTO(
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        "METODO_NAO_SUPORTADO",
                        "Metodo nao suportado",
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Requisicao invalida", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "REQUISICAO_INVALIDA",
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGeneric(Exception ex) {
        log.error("Erro inesperado no servidor", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorDTO(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "ERRO_INTERNO",
                        "Erro inesperado no servidor",
                        LocalDateTime.now()
                ));
    }
}
