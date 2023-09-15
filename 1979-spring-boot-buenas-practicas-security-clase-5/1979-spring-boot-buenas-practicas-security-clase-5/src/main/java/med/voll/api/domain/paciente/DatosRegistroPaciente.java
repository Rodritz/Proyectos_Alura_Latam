package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccionPaciente;

public record DatosRegistroPaciente(

        @NotBlank(message = "{nombre.obligatorio}")
        String nombre,
        @NotBlank(message = "{email.obligatorio}")
        @Email(message = "{email.invalido}")
        String email,
        @NotBlank(message = "{telefono.obligatorio}")
        String telefono,
        @NotBlank(message = "{documento.obligatorio}")
        @Pattern(regexp = "\\d{4,7}", message = "{documento.invalido}")
        String documento,
        @NotNull(message = "{direccion.obligatorio}")
        @Valid
        DatosDireccionPaciente direccion) {
}
