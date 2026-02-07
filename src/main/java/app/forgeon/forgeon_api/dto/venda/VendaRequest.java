package app.forgeon.forgeon_api.dto.venda;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VendaRequest {

    @NotNull
    private UUID produtoPublicId;

    @NotNull
    @Min(1)
    private Integer quantidade;
}
