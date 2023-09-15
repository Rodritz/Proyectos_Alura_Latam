package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DireccionPaciente {

    private String calle;
    private String numero;
    private String localidad;
    private String provincia;
    private String cp;


    public DireccionPaciente(DatosDireccionPaciente direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.localidad = direccion.localidad();
        this.provincia = direccion.provincia();
        this.cp = direccion.cp();
    }

    public DireccionPaciente actualizarInformacion(DatosDireccionPaciente direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.localidad = direccion.localidad();
        this.provincia = direccion.provincia();
        this.cp = direccion.cp();
        return this;
    }
}
