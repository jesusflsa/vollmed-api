package med.voll.api.domain.consultas;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.desafio.ValidadorCancelamientoConsultas;
import med.voll.api.domain.consultas.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendarConsultasService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidadorDeConsultas> validadores;

    @Autowired
    private List<ValidadorCancelamientoConsultas> validadoresCancelamiento;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {

        if (pacienteRepository.findById(datos.idPaciente()).isEmpty()) {
            throw new ValidacionDeIntegridad("El id del paciente no fue encontrado");
        }

        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("El id del medico no fue encontrado");
        }

        validadores.forEach(v -> v.validar(datos));
        var paciente = pacienteRepository.getReferenceById(datos.idPaciente());
        var medico = seleccionarMedico(datos);

        if (medico == null) {
            throw new ValidacionDeIntegridad("No hay ningún médico disponible en esa fecha");
        }

        var consulta = new Consulta(null, medico, paciente, datos.fecha());
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if (datos.especialidad() == null) {
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el médico");
        }

        var horarioMinimo = datos.fecha().minusHours(1).plusMinutes(1);
        var horarioMaximo = datos.fecha().plusHours(1).minusMinutes(1);

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), horarioMinimo, horarioMaximo);
    }

    public void cancelar(DatosCancelarConsulta datos) {
        if (!consultaRepository.existsById(datos.id())) {
            throw new ValidationException("La consulta no existe");
        }

        validadoresCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.id());


    }
}
