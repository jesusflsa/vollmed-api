package med.voll.api.domain.consultas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCancelarConsulta(
        @NotNull(message = "Id de la consulta requerido")
        Long id,

        @NotBlank(message = "Motivo requerido")
        String motivo
) {}
