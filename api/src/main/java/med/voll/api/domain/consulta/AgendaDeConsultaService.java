package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorCancelacionDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired //aca injectamos la interface de manera que si alguna clase validadcion se elimina o agrega, no hay que modificar nada
    List<ValidadorDeConsultas> validadores;

    @Autowired
    List<ValidadorCancelacionDeConsulta> validadorCancelacion;


    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){

        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("esta id para el paciente no fue encontrado");
        }

        if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        //validaciones=>una vez que creamos las validaciones solo queda llamarlas en el metodo
        validadores.forEach(v->v.validar(datos));

        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).get();

        Medico medico = seleccionarMedico(datos);
        if(medico==null){
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario y especialidad");
        }

        Consulta consulta = new Consulta(paciente,medico,datos.fecha());
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    public void cancelar(DatosCancelacionDeConsulta datos){
        if(!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("Esta consulta no fue encontrado");
        }
        validadorCancelacion.forEach(v->v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionar una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}
