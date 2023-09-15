package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.DireccionPaciente;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;

    @Embedded
    private DireccionPaciente direccion;

    public Paciente(DatosRegistroPaciente datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.direccion = new DireccionPaciente(datos.direccion());
    }

    public void actualizarInformacion(DatosActualizacionPaciente datosActualizacionPaciente) {
        if (datosActualizacionPaciente.nombre() != null)
            this.nombre = datosActualizacionPaciente.nombre();

        if (datosActualizacionPaciente.telefono() != null)
            this.telefono = datosActualizacionPaciente.telefono();

        if (datosActualizacionPaciente.direccion() != null)
            this.direccion = direccion.actualizarInformacion(datosActualizacionPaciente.direccion());
    }

    public void inactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }
}
