package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);


    /* el sig metodo no funcionaria solo ya que deberia tener su nombre en ingles y deberia comenzar con FindBy+alguno de los atributos de la clase Medico
     es por eso que hacemos uso de la anotacio @Query en la que nosotros podemos hacer consultas en la DB */
    /*
    -la consulta se escribe entre comillas triples
    -traduccion de la consulta:
    seleccione todos los medicos que se encuentren activos
    y que contengan esa especialdad.
    -para que retorne solo aquellos que esten disponibles creamos
    una nueva consulta que va a retornar aquellos id.medico
    que no tengan una consulta agendada en dicha fecha
    -order by rand los ordena de forma aleatoria
    -limit 1 solo va a retornar un medico
     */
    /*El caracter dos-puntos (:) indica un parámetro dinámico en la query. ej c.fecha= :fecha*/
    @Query("""
            select m from Medico m
            where m.activo= 1
            and
            m.especialidad= :especialidad
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.fecha= :fecha
            )
            order by rand()
            limit 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);

    @Query("""
            select m.activo
            from Medico m
            where m.id=:idMedico
            """)
    Boolean findActivoById(Long idMedico);
}
