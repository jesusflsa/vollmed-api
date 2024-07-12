package med.voll.api.domain.consultas;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(
        @JsonAlias("id_paciente")
        Long idPaciente,

        @JsonAlias("id_medico")
        Long idMedico,

        @NotNull
        @Future
        LocalDateTime fecha,

        Especialidad especialidad
) {}
