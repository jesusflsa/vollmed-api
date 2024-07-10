package med.voll.api.domain.consultas;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.Paciente;
import med.voll.api.domain.pacientes.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendarConsultasService {

    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    public void agendar(DatosAgendarConsulta datos) {

        if (pacienteRepository.findById(datos.idPaciente()).isEmpty()) {
            throw new ValidacionDeIntegridad("El id del paciente no fue encontrado");
        }

        if (datos.idMedico() != null && medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("El id del medico no fue encontrado");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        var consulta = new Consulta(null, medico, paciente, datos.fecha());
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if (datos.especialidad() == null) {
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el médico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }
}
