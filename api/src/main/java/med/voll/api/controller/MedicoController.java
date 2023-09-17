package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder){
          Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),
                medico.getNombre(), medico.getEmail(),medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(),new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));
        //URI url = "http://localhost:8008/medicos/" + medico.getId(); //en lugar de esto usamos el UriComponentBuilder
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        //RETURN 201 CREATED + URL DONDE ENCONTRAR AL MEDICO
        return ResponseEntity.created(url).body(datosRespuestaMedico);

    }

    //con el sig metodo returnabamos un listado de medicos
    /*@GetMapping
    public List<DatosListadoMedico> listadoMedicos(){
        return medicoRepository.findAll().stream().map(DatosListadoMedico::new).toList();
    }*/

    //modificamos el metodo anterior para retornar los datos en paginas
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>>  listadoMedicos(@PageableDefault(page = 0, size = 15, sort = {"id"})Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new); //retorna todos los registros
        //RETURN 200 OK
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new)); //retorna solo los registros que tengan activo=true
    }
    /*IMPORTANTE:
    si queremos trabajar la request para postman modificando parametros seria de la sig manera
    http://localhost:8080/medicos?size=10&page=0&sort=nombre
    siendo size la cant de registros que queremos mostrar por pagina,
    page la pagina que queremos mostrar y sort que filtro queremos aplicar....

    pero la interfaz Pageable por default viene con un size de 20 que puede modificarse
    dentro de los parametros del metodo con @PageableDefault(size=?)
     */

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        //RETURN 200 OK
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),medico.getDireccion().getComplemento())));

    }

    /*--------DELETE EN BASE DE DATOS------
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }*/

    //--DELETE LOGIGO--(solo cambia el estado del registro para que no salga listado)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        //RETURN 204 NO CONTENT
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity activarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.activarMedico();
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),
                medico.getNombre(), medico.getEmail(),medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(),new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));
        //RETURN 200 OK
        return ResponseEntity.ok(datosRespuestaMedico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),
                medico.getNombre(), medico.getEmail(),medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(),new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));
        //RETURN 200 OK
        return ResponseEntity.ok(datosRespuestaMedico);
    }

}
