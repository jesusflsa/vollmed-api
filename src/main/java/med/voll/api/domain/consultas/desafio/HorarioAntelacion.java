package med.voll.api.domain.consultas.desafio;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DatosCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioAntelacion implements ValidadorCancelamientoConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelarConsulta datos) {
        var consulta = consultaRepository.getReferenceById(datos.id());
        var fechaInvalida = Duration.between(LocalDateTime.now(), consulta.getFecha()).toHours() < 24;

        if (fechaInvalida) {
            throw new ValidationException("Las consultas solo pueden ser canceladas con 24 horas de anticipación");
        }
    }
}
