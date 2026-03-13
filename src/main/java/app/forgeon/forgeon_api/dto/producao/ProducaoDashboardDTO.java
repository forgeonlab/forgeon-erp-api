package app.forgeon.forgeon_api.dto.producao;

public record ProducaoDashboardDTO(

        long total,
        long planejadas,
        long emProducao,
        long finalizadas,
        long pausadas

) {}