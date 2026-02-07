package app.forgeon.forgeon_api.exception.auth;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDatabaseError(DataAccessException ex) {
        return ResponseEntity.status(500).body(
                Map.of(
                        "error", "Erro de banco",
                        "message", ex.getMostSpecificCause().getMessage()
                )
        );
    }
}
