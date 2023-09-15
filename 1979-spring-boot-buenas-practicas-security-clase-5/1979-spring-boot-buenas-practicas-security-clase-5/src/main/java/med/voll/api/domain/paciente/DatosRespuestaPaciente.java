package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DireccionPaciente;

public record DatosRespuestaPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DireccionPaciente direccion) {
}
