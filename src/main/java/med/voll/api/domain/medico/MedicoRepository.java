package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByActivoTrue(Pageable page);


    @Query("""
            SELECT m FROM Medico m
            WHERE m.activo = TRUE AND
            m.especialidad = :especialidad AND
            m.id not in (
            SELECT c.medico.id FROM Consulta c
            WHERE c.fecha BETWEEN :fechaApertura AND :fechaCierre
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fechaApertura, LocalDateTime fechaCierre);

    @Query("""
            SELECT m.activo from Medico m
            where m.id = :idMedico
            """)
    Boolean findActivoById(Long idMedico);
}
