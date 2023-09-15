package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccionPaciente;

public record DatosActualizacionPaciente(
        @NotNull Long id,
        String nombre,
        String telefono,
        DatosDireccionPaciente direccion) {
}
