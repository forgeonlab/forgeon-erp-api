package app.forgeon.forgeon_api.exception;

public class SkuDuplicadoException extends RuntimeException {

    public SkuDuplicadoException(String sku) {
        super("SKU já existe: " + sku);
    }
}
