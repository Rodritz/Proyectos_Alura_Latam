package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccionPaciente(
        @NotBlank
        String calle,
        @NotBlank
        String numero,
        @NotBlank
        String localidad,
        @NotBlank
        String provincia,
        @NotBlank
        String cp) {
}
