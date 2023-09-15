package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DireccionPaciente;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrar(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                            UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DireccionPaciente(paciente.getDireccion().getCalle(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getLocalidad(), paciente.getDireccion().getProvincia(),
                        paciente.getDireccion().getCp()));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        //RETURN 201 CREATED + URL DONDE ENCONTRAR AL MEDICO
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>>  listar(@PageableDefault(page = 0, size = 10, sort = {"id"}) Pageable paginacion) {
        //return pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new);
        //RETURN 200 OK
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new)) ;
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos) {
        Paciente paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarInformacion(datos);
        //RETURN 200 OK
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DireccionPaciente(paciente.getDireccion().getCalle(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getLocalidad(), paciente.getDireccion().getProvincia(),
                        paciente.getDireccion().getCp())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity removerPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.inactivar();
        //RETURN 204 NO CONTENT
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity activarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.activar();
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DireccionPaciente(paciente.getDireccion().getCalle(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getLocalidad(), paciente.getDireccion().getProvincia(),
                        paciente.getDireccion().getCp()));
        //RETURN 200 OK
        return ResponseEntity.ok(datosRespuestaPaciente);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> retornaDatosPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DireccionPaciente(paciente.getDireccion().getCalle(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getLocalidad(), paciente.getDireccion().getProvincia(),
                        paciente.getDireccion().getCp()));
        //RETURN 200 OK
        return ResponseEntity.ok(datosRespuestaPaciente);
    }



    /*public void validarSiExiste(Long id) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);

        if (!pacienteOptional.isPresent()) {
            // Puedes lanzar una excepción o realizar alguna acción si el paciente no existe
            throw new PacienteNotFoundException("El paciente con ID " + id + " no se encontró.");
        }
        // Si llegamos aquí, el paciente existe
    }*/
}