package med.voll.api.domain.consultas.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datos) {

        if (datos.idMedico() == null) return;

        var horarioMinimo = datos.fecha().minusHours(1).plusMinutes(1);
        var horarioMaximo = datos.fecha().plusHours(1).minusMinutes(1);

        var medicoConConsulta = consultaRepository.existsByMedicoIdAndFechaBetween(datos.idMedico(), horarioMinimo, horarioMaximo);

        if (medicoConConsulta)
            throw new ValidationException("No permitir programar una cita con un médico que ya tiene otra cita programada en la misma fecha/hora");

    }
}
