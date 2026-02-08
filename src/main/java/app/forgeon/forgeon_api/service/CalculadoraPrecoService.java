package app.forgeon.forgeon_api.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculadoraPrecoService {

    public BigDecimal calcularPreco(
            BigDecimal custoBase,
            BigDecimal porcentagemLucro,
            BigDecimal porcentagemMarketplace
    ) {
        BigDecimal lucro = custoBase.multiply(porcentagemLucro)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal preco = custoBase.add(lucro);

        if (porcentagemMarketplace != null) {
            BigDecimal taxa = preco.multiply(porcentagemMarketplace)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            preco = preco.add(taxa);
        }

        return preco.setScale(2, RoundingMode.HALF_UP);
    }
}
