package med.voll.api.domain.pacientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("""
            SELECT p.activo from Paciente p
            where p.id = :idPaciente
            """)
    Boolean findActivoById(Long idPaciente);

    Page<Paciente> findAllByActivoTrue(Pageable page);
}